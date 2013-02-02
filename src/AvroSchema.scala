package org.apache.avro.scala.schema

import org.apache.avro.Schema
import scala.collection.JavaConversions._

object AvroSchema {
  def apply[CC <: Product](obj: CC): Schema = {
    val s = Schema.createRecord(recordName(obj), null, recordNamespace(obj), false);
    s.setFields(List())
    s
  }

  def recordName[CC <: Product](obj: CC): String = {
    val name = obj.getClass.getSimpleName
    if (name.contains("$")) {
      throw new Exception(s"Classes defined in anonymous functions (such as ${name}, in this case) may not be used to generate Avro record schemas because it is difficult to determine the proper name of the corresponding Avro record.")
    }
    name
  }

  def recordNamespace[CC <: Product](obj: CC): String =
    obj.getClass.getPackage.getName
}
