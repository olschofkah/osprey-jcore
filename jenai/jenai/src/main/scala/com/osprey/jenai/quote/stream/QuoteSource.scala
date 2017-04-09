package com.osprey.jenai.quote.stream

import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}
import akka.stream.{ActorMaterializer, ThrottleMode}
import akka.{Done, NotUsed}
import com.osprey.jenai.JenaiContext
import com.osprey.jenai.quote.YahooQuoteUrl
import com.osprey.jenai.utils.Logging

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class QuoteSource(val symbol: String)(implicit ctx: JenaiContext) extends Logging {


  implicit val executionContext: ExecutionContext = ctx.executionContext
  implicit val mat: ActorMaterializer = ctx.actorMaterializer
  implicit val system: ActorSystem = ctx.actorSystem

  val quoteUrl = new YahooQuoteUrl(symbol, ctx.appConfig)


  def source(throttle: Int): Source[(Try[HttpResponse], Int), NotUsed] = {

    val pooledConnectionFlow: Flow[(HttpRequest, Int), (Try[HttpResponse], Int), Http.HostConnectionPool] =
      Http().cachedHostConnectionPool[Int](host = quoteUrl.domain)

    val throttleFlow: Flow[(Try[HttpResponse], Int), (Try[HttpResponse], Int), NotUsed] =
      Flow[(Try[HttpResponse], Int)].throttle(1, FiniteDuration(throttle, TimeUnit.MILLISECONDS), 1, ThrottleMode.shaping)

    val logFlow: Flow[(Try[HttpResponse], Int), (Try[HttpResponse], Int), NotUsed] =
      Flow[(Try[HttpResponse], Int)].map(t => {
        logger.info("log flow: {}", t)
        t
      })

    val httpRequestSource: Source[(HttpRequest, Int), NotUsed] = Source.repeat(HttpRequest(uri = quoteUrl.uri) -> 0)

    httpRequestSource via pooledConnectionFlow via logFlow via throttleFlow
  }

}