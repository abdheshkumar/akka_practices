package com.abtechsoft

import com.typesafe.config.ConfigFactory
import akka.actor._
import java.util.Scanner

object RemoteApplication extends App {
  var originalSender: ActorRef = _

  val configString = """akka {
                       |  loglevel = "INFO"
                       |  actor {
                       |    provider = "akka.remote.RemoteActorRefProvider"
                       |  }
                       |  remote {
                       |    enabled-transports = ["akka.remote.netty.tcp"]
                       |    netty.tcp {
                       |      hostname = "127.0.0.1"
                       |      port = 5150
                       |    }
                       |    log-sent-messages = on
                       |    log-received-messages = on
                       |  }
                       |}""".stripMargin
  val customConf = ConfigFactory.parseString(configString)
  val remoteSystem = ActorSystem("RemoteApplication", customConf)
  val remoteActorRef = remoteSystem.actorOf(Props[RemoteActor], "remote")
  val scanner = new Scanner(System.in)
  println("Waiting for message from Local")
  while (true) {

    val input = scanner.nextLine()
    originalSender ! Get(input)
  }

}

class RemoteActor extends Actor {

  def receive = {
    case msg: Send =>
      println("------------------------------------------------------")
      println("Message Received from Local:" + msg.message)
      RemoteApplication.originalSender = sender
      println("------------------------------------------------------")
  }

}

case class Send(message: String)

case class Get(message: String)