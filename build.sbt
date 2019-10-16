name := "scalatest-matchers"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5",
  "com.ironcorelabs" %% "cats-scalatest" % "2.4.0"
).map(_ % Test)