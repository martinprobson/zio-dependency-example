ThisBuild / organization := "net.martinprobson.example"
ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file(".")).settings(
  name := "zio-dependency-example",
  libraryDependencies ++= Seq(
    // zio
    "dev.zio" %% "zio-interop-cats" % "23.0.03",
    "dev.zio" %% "zio" % "2.1.13",
    // zio logging
    "dev.zio" %% "zio-logging" % "2.4.0",
    "dev.zio" %% "zio-logging-slf4j" % "2.4.0",
    // ZIO Config
//    "dev.zio" %% "zio-config" % "3.0.7",
//    "dev.zio" %% "zio-config-magnolia" % "3.0.7",
//    "dev.zio" %% "zio-config-typesafe" % "3.0.7",
    "dev.zio" %% "zio-config" % "4.0.2",
    "dev.zio" %% "zio-config-magnolia" % "4.0.2",
    "dev.zio" %% "zio-config-typesafe" % "4.0.2",
// Logging (Cats)
    "ch.qos.logback" % "logback-classic" % "1.5.12",
    "ch.qos.logback" % "logback-core" % "1.5.12",
    "org.typelevel" %% "log4cats-slf4j" % "2.5.0",
    // Doobie
    "org.tpolecat" %% "doobie-core" % "1.0.0-RC1",
    "org.tpolecat" %% "doobie-hikari" %  "1.0.0-RC1",
    // Db Drivers
    "mysql" % "mysql-connector-java" % "8.0.33",
    "com.h2database" % "h2" % "1.4.200",
    // Testing
    "dev.zio" %% "zio-test" % "2.1.13" % Test,
    "dev.zio" %% "zio-test-sbt" % "2.1.13" % Test
  )
)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

lazy val compilerOptions = Seq(
  "-deprecation",         // Emit warning and location for usages of deprecated APIs.
  "-explaintypes",        // Explain type errors in more detail.
  "-explain",
  "-Xfatal-warnings",     // Fail the compilation if there are any warnings.
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions
)
