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

    val fields = cc.members
      .map(_.asTerm)
      .filter(f => f.isCaseAccessor && f.isGetter).toList
      .map { f =>
        reify {
          new Schema.Field(
            c.Expr[String](Literal(Constant(f.name.encoded))).splice,
            Schema.create(Schema.Type.STRING),
            null,
            null
          )
        }.tree
      }

    reify {
      val s = Schema.createRecord(
        c.Expr[String](Literal(Constant(recordName))).splice,
        null,
        c.Expr[String](Literal(Constant(recordNamespace))).splice,
        false
      )
      s.setFields(
        c.Expr[List[Schema.Field]](Apply(Ident(newTermName("List")), fields)).splice
      )
      s
    }
  }
}
