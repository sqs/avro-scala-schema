name := "avro-scala-schema"

scalaVersion := "2.10.0"

libraryDependencies += "org.apache.avro" % "avro" % "1.7.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0.M5b"

scalaSource in Compile <<= baseDirectory(_ / "src")

scalaSource in Test <<= baseDirectory(_ / "test")
