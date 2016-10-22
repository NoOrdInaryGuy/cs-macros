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
  licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))
)

lazy val root = project.in(file(".")).
  settings(commonSettings: _*).
  settings(
    name := "callable-statement-macros",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test"
//    scalacOptions ++= Seq("-Xprint:typer")
  )

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
