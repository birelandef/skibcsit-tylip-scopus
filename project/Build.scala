import sbt._
import Keys._

object ApplicationBuild extends Build {

  val scalaVersion = "2.11.6"

  //Repos
  val TypeSafe =
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  val ScalazRepo =
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

  val playVersion = "2.4.2"

  //Deps
  val PlayJson = "com.typesafe.play" %% "play-json" % playVersion
  val WS = "com.typesafe.play" %% "play-ws" % playVersion
  val Specs = "org.specs2" %% "specs2-core" % "3.6" % "test"

  mainClass in (Compile, run) := Some("com.tylip.adapters.scopus.WSExample")

  lazy val root = sbt.Project(
    id = "tylip-scopus",
    base = file("."),
    settings = Defaults.defaultConfigs ++ Seq(
      sbt.Keys.organization := "com.tylip",
      sbt.Keys.scalaVersion := scalaVersion,
      sbt.Keys.version := "0.1-SNAPSHOT",
      sbt.Keys.scalacOptions++= Seq("-feature", "-deprecation"),
      sbt.Keys.resolvers ++= Seq(
        TypeSafe, ScalazRepo
      ),
      sbt.Keys.libraryDependencies ++= Seq(
        WS,
        PlayJson,
        Specs
      )
    )
  )
}
