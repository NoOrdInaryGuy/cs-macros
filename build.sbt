import sbt._
import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "io.noordinaryguy",
  scalaVersion := "2.11.7",
  scalacOptions := Seq("-unchecked", "-deprecation"),
  version := "0.0.1-SNAPSHOT",
  useGpg := true,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/NoOrdInaryGuy"))
)

lazy val macros = project.in(file("macros")).
  settings(commonSettings: _*).
  settings(
    name := "callable-statement-macros",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"
  )

lazy val samples = project.in(file("samples")).
  settings(commonSettings: _*).
  settings(
    name := "callable-statement-samples",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.7",
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12",
    scalacOptions ++= Seq("-Xprint:typer")
  ).
  dependsOn(macros)

lazy val root = project.in(file(".")).aggregate(macros, samples)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
