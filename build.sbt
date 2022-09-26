ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.0"

val http4sVersion = "0.23.16"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.3.14",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % "0.14.3",
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.14.3",
  "ch.qos.logback" % "logback-classic" % "1.4.1"
)


lazy val root = (project in file("."))
  .settings(
    name := "Scala_Functional_Microservice"
  )
