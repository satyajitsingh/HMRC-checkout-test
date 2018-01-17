import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(Seq(
    organization := "com.backwards",
    scalaVersion := "2.12.3",
    version      := "0.1.0-SNAPSHOT"
  )),
  name := "movie-parental-control-by-david-ainslie",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:existentials",
    "-language:reflectiveCalls",
    "-language:postfixOps",
    "-Ypartial-unification",
    "-Yrangepos",
    "-Yrepl-sync"
  ),
  libraryDependencies ++= Seq(
    specs2 % Test,
    specs2Mock % Test,
    specs2ScalaCheck % Test
  ),
  libraryDependencies ++= Seq(
    shapeless,
    cats
  )
)