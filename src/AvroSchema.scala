package org.apache.avro.scala.schema

import org.apache.avro.Schema
import scala.collection.JavaConversions._
import scala.language.experimental.macros
import scala.reflect.macros.Context

object AvroSchema {
  def from[CC <: Product]: Schema = macro applyImpl[CC]

  def applyImpl[CC : c.WeakTypeTag](c: Context): c.Expr[Schema] = {
    import c.universe._

    val cc = weakTypeOf[CC]
    val caseClass = cc.typeSymbol.asClass

    val recordName = {
      if (caseClass.name.encoded != caseClass.name.decoded) {
        throw new Exception(s"Can't generate Avro schema for class with non-alphanumeric name: '${caseClass.name.decoded}'")
      }
      caseClass.name.encoded
    }
    val recordNamespace = caseClass.owner.fullName

    val fields = cc.declarations.sorted
      .map(_.asTerm)
      .filter(f => f.isCaseAccessor && f.isGetter).toList
      .map { f =>
        new Schema.Field(
          f.name.encoded,
          Schema.create(f.asMethod.returnType.typeConstructor.toString match {
            case "String" => Schema.Type.STRING
            case "Int" => Schema.Type.INT
            case "Long" => Schema.Type.LONG
            case "Float" => Schema.Type.FLOAT
            case "Double" => Schema.Type.DOUBLE
            case "Boolean" => Schema.Type.BOOLEAN
          }),
          null,
          null
        )
    }

    val s = Schema.createRecord(recordName, null, recordNamespace, false)
    s.setFields(fields)
    reify {
      new Schema.Parser().parse(
        c.Expr[String](Literal(Constant(s.toString))).splice
      )
    }
  }
}
