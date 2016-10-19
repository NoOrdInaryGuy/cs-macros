import sbt._
import sbt.Keys._

lazy val commonSettings = Seq(
  scalaVersion := "2.11.7",
  organization := "callable-statement-macros",
  version := "0.0.1-SNAPSHOT"
)

lazy val macros = project.in(file("macros")).
  settings(commonSettings: _*).
  settings(
    name := "macros",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"
  )

lazy val samples = project.in(file("samples")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.7",
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12",
    scalacOptions ++= Seq("-Xprint:typer")
  ).
  dependsOn(macros)

lazy val root = project.in(file("."))
