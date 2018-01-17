import sbt._

object Dependencies {
  lazy val specs2Version = "4.0.0"

  lazy val specs2: ModuleID = "org.specs2" %% "specs2-core" % specs2Version
  lazy val specs2Mock: ModuleID = "org.specs2" %% "specs2-mock" % specs2Version
  lazy val specs2ScalaCheck: ModuleID = "org.specs2" %% "specs2-scalacheck" % specs2Version
  lazy val shapeless: ModuleID = "com.chuusai" %% "shapeless" % "2.3.2"
  lazy val cats: ModuleID = "org.typelevel" %% "cats-core" % "1.0.0-MF"
}