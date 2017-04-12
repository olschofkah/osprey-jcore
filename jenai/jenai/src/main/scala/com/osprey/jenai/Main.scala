package com.osprey.jenai

import java.time.LocalDateTime

import akka.{Done, NotUsed}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.scaladsl.{RunnableGraph, Sink}
import com.osprey.jenai.quote.stream.QuoteSource
import com.osprey.jenai.utils.Logging

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

object Main extends App with Logging {
  println("Hello master ... what shall I do?")

  implicit val ctx = JenaiContext()
  implicit val mat = ctx.actorMaterializer

  val quoteSource = new QuoteSource("GDXJ")

  val src = quoteSource.source(throttle = 25)

  val quoteSink: Sink[(Try[HttpResponse], Int), Future[Done]] = Sink.foreach[(Try[HttpResponse], Int)](elem =>
    elem._1 match {
      case Success(r) =>
        val quote: Future[String] = Unmarshal(r.entity).to[String]
        quote
      case Failure(e) =>
        logger.info(s"Failure received: ${e}")
    })


  // // NOTE: Some black magic going on here with tcp buffering due to auto backpressure management ... must consume entity or it block future requests.
  //  val entityDrain: Sink[(Try[HttpResponse], Int), Future[Done]] = Sink.foreach[(Try[HttpResponse], Int)](elem =>
  //    elem._1 match {
  //      case Success(r) =>
  //        r.entity.dataBytes.runWith(Sink.ignore)
  //      case Failure(e) =>
  //        logger.info(s"Failure received: ${e}")
  //    })

  src to quoteSink run()

  Await.ready(ctx.actorSystem.whenTerminated, Duration.Inf)
}