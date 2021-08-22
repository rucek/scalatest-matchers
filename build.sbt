name := "scalatest-matchers"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.9",
  "com.ironcorelabs" %% "cats-scalatest" % "3.1.1"
).map(_ % Test)