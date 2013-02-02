package org.apache.avro.scala.schema

import org.apache.avro.Schema
import scala.collection.JavaConversions._

trait AvroType {
  caseClass: Product =>

  lazy val avroSchema: Schema = {
    val s = Schema.createRecord(recordName, null, recordNamespace, false);
    s.setFields(List())
    s
  }

  private def recordName: String = {
    val name = caseClass.getClass.getSimpleName
    if (name.contains("$")) {
      throw new Exception(s"Classes defined in anonymous functions (such as ${name}, in this case) may not be used to generate Avro record schemas because it is difficult to determine the proper name of the corresponding Avro record.")
    }
    name
  }

  private def recordNamespace: String = caseClass.getClass.getPackage.getName
}
