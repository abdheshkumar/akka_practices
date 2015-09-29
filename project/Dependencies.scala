import sbt._

object Version {
  val scala = "2.11.5"
  val akka = "2.3.11"
  val slf4j = "1.7.2"
  val logback = "1.0.7"
  val twitter4jV = "4.0.2"
  val akkaStream = "1.0"
  val scalatestV = "2.2.4"
  val mockito   = "1.10.19"
}

object Library {
  import Version._
  val akkaActor      = "com.typesafe.akka" %% "akka-actor"      % akka
  val akkaCamel      = "com.typesafe.akka" %% "akka-camel"      % akka
  val akkaRemote     = "com.typesafe.akka" %% "akka-remote"     % akka
  val akkaTestkit    = "com.typesafe.akka" %% "akka-testkit"    % akka
  val akkaKernel     = "com.typesafe.akka" %% "akka-kernel"     % akka
  val slf4jApi       = "org.slf4j"          % "slf4j-api"       % slf4j
  val logbackClassic = "ch.qos.logback"     % "logback-classic" % logback
  val twitter4j      = "org.twitter4j"      % "twitter4j-core"  % twitter4jV
  val akkastream     = "com.typesafe.akka" %% "akka-stream-experimental" % akkaStream
  val scalatest      = "org.scalatest"     %% "scalatest"       % scalatestV
  val mockitoAll     = "org.mockito"       %  "mockito-all"     % mockito
}

object Dependencies {
  import Library._
  val dependencies = Seq(
    akkaActor,akkaCamel,akkaRemote,akkaKernel,akkaTestkit,akkastream,
    slf4jApi,
    logbackClassic,
    scalatest % "test",
    mockitoAll % "test"
  )
}