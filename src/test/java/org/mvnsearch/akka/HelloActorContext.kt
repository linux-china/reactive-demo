@file:Suppress("HasPlatformType")

package org.mvnsearch.akka

import akka.actor.AbstractLoggingActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.japi.pf.ReceiveBuilder

/**
 * hello actor
 *
 * @author linux_china
 */
class HelloKotlinActor : AbstractLoggingActor() {
    override fun createReceive() = ReceiveBuilder().match(String::class.java) { log().info("Hello $it") }.build()
}


fun main() {
   /* val actorSystem = ActorSystem.create("actorSystem1")
    val helloActor = actorSystem.actorOf(Props.create(HelloKotlinActor::class.java))
    helloActor.tell("jackie", ActorRef.noSender())
    Thread.sleep(1000)
    actorSystem.terminate()*/
}

