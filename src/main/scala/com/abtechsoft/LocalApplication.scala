package com.abtechsoft

import com.typesafe.config.ConfigFactory
import akka.actor._
import java.util.Scanner

object LocalApplication extends App {

  val configString = """akka {
                       |  loglevel = "INFO"
                       |
                       |  actor {
                       |    provider = "akka.remote.RemoteActorRefProvider"
                       |  }
                       |
                       |  remote {
                       |    enabled-transports = ["akka.remote.netty.tcp"]
                       |    netty.tcp {
                       |      hostname = "127.0.0.1"
                       |      port = 0
                       |    }
                       |
                       |    log-sent-messages = on
                       |    log-received-messages = on
                       |  }
                       |
                       |}""".stripMargin

  val customConf = ConfigFactory.parseString(configString)
  val system = ActorSystem("LocalSystem", ConfigFactory.load(customConf))
  val addr = Address("akka.tcp", "RemoteApplication", "127.0.0.1", 5150)
  val path = RootActorPath(addr) / "user" / "remote"
  //val remoteActorReference = system.actorSelection("akka.tcp://RemoteApplication@127.0.0.1:5150/user/remote")
  val remoteActorReference = system.actorSelection(path)
  val local = system.actorOf(Props(new LocalActor(remoteActorReference)))
  val scanner = new Scanner(System.in)

  println("Send message to Remote")

  while (true) {

    val input = scanner.nextLine
    local ! Send(input)

  }
}

class LocalActor(remote: ActorSelection) extends Actor {
  def receive = {
    case msg: Send =>
      //      println("Sending message to " + remote)
      remote ! msg
    case msg: Get =>
      println("----------------------------------------------------------")
      println("Message Received from Remote :" + msg.message)
      println("----------------------------------------------------------")
  }

}