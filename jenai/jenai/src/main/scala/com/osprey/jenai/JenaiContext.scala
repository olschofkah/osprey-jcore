package com.osprey.jenai

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}
import akka.stream.Supervision.Decider
import com.osprey.jenai.utils.Logging
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContext

class JenaiContext extends Logging {


  val decider: Decider = {
    case ex =>
      logger.error(ex)
      Supervision.Stop // Passes error down to subscriber
  }

  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer(ActorMaterializerSettings(actorSystem).withSupervisionStrategy(decider))
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher
  val appConfig: Config = ConfigFactory.load()
}

object JenaiContext {

  def apply(): JenaiContext = {
    new JenaiContext()
  }

}