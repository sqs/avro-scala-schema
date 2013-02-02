package org.apache.avro.scala.schema

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

case class EmptyRecord()

class AvroSchemaTest extends FunSpec with ShouldMatchers {
  describe("EmptyRecord") {
    it("should generate the corresponding Avro schema") {
      AvroSchema.from[EmptyRecord].toString should be ("""{"type":"record","name":"EmptyRecord","namespace":"org.apache.avro.scala.schema","fields":[]}""")
    }
  }
}
