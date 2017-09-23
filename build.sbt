scalaVersion in ThisBuild := "2.12.3"
scalafmtOnCompile in ThisBuild := true

lazy val commonSettings = Seq(
  organization := "my.will.be.done",
  scalacOptions ++= Seq("-deprecation", "-feature", "-Xlint")
)

lazy val model = crossProject
  .crossType(CrossType.Pure)
  .settings(
    commonSettings
  )

lazy val modelJVM = model.jvm
lazy val modelJS = model.js

val Version = new {
  val circe = "0.8.0"
}

lazy val client = crossProject
  .crossType(CrossType.Pure)
  .settings(
    commonSettings,
    libraryDependencies ++= {
      Seq(
        "fr.hmil" %%% "roshttp" % "2.0.2",
        "io.circe" %%% "circe-generic-extras" % Version.circe,
        "io.circe" %%% "circe-parser" % Version.circe
      )
    }
  )
  .dependsOn(model)

lazy val clientJVM = client.jvm
lazy val clientJS = client.js

lazy val root = project
  .in(file("."))
  .aggregate(clientJS, clientJVM, modelJS, modelJVM)
