package org.apache.avro.scala.schema

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

case class EmptyRecord()
case class RecordWithString(a: String)

class AvroSchemaTest extends FunSpec with ShouldMatchers {
  describe("EmptyRecord") {
    it("should generate the corresponding Avro schema") {
      AvroSchema.from[EmptyRecord].toString should be ("""{"type":"record","name":"EmptyRecord","namespace":"org.apache.avro.scala.schema","fields":[]}""")
    }
  }

  describe("RecordWithString") {
    it("should generate the corresponding Avro schema") {
      AvroSchema.from[RecordWithString].toString should be ("""{"type":"record","name":"RecordWithString","namespace":"org.apache.avro.scala.schema","fields":[{"name":"a","type":"string"}]}""")
    }
  }
}
