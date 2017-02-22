import sbt._

object Dependencies {
  lazy val circeCore = "io.circe" %% "circe-core" % circeVersion
  lazy val circeParser = "io.circe" %% "circe-parser" % circeVersion
  lazy val specs2Core = "org.specs2" %% "specs2-core" % specs2Version
  lazy val fs2Http = "com.spinoco" %% "fs2-http" % fs2HttpVersion
  
  val specs2Version = "3.8.8"
  val circeVersion = "0.7.0"
  val fs2HttpVersion = "0.1.4"
}
