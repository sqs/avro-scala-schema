package org.apache.avro.scala.schema

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

case class EmptyRecord() extends AvroType

class AvroTypeTest extends FunSpec with ShouldMatchers {
  describe("EmptyRecord") {
    it("should generate the corresponding Avro schema") {
      EmptyRecord().avroSchema.toString should be ("""{"type":"record","name":"EmptyRecord","namespace":"org.apache.avro.scala.schema","fields":[]}""")
    }
  }
}
