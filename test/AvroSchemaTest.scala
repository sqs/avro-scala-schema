package org.apache.avro.scala.schema

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

case class EmptyRecord()
case class RecordWithString(a: String)
case class RecordWith2Strings(a: String, b: String)
case class RecordWithPrimitives(a: String, b: Int, c: Long, d: Float, e: Double, f: Boolean)

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

  describe("RecordWith2Strings") {
    it("should generate the corresponding Avro schema") {
      AvroSchema.from[RecordWith2Strings].toString should be ("""{"type":"record","name":"RecordWith2Strings","namespace":"org.apache.avro.scala.schema","fields":[{"name":"a","type":"string"},{"name":"b","type":"string"}]}""")
    }
  }

  describe("RecordWithPrimitives") {
    it("should generate the corresponding Avro schema") {
      AvroSchema.from[RecordWithPrimitives].toString should be ("""{"type":"record","name":"RecordWithPrimitives","namespace":"org.apache.avro.scala.schema","fields":[{"name":"a","type":"string"},{"name":"b","type":"int"},{"name":"c","type":"long"},{"name":"d","type":"float"},{"name":"e","type":"double"},{"name":"f","type":"boolean"}]}""")
    }
  }
}
