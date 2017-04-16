package com.osprey.jenai.model

import java.time.{LocalDate, ZonedDateTime}

import com.osprey.securitymaster.HistoricalQuote

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Created by Goliaeth on 4/13/2017.
  */
class QuoteGenerator(symbol: String, bidSeed: Double, askSeed: Double) {

  private val random = new Random()

  def generateHistoricalQuotes(size: Int): List[HistoricalQuote] = {
    val dt = LocalDate.now()
    val lb = new ListBuffer[HistoricalQuote]()
    var previousBidAskLast = generateQuote(bidSeed, askSeed)

    // TODO needs work


    for (i <- 0 until size) {


      val quote = new HistoricalQuote(symbol, dt)
      quote.setClose(previousBidAskLast._3)
      quote.setAdjClose(previousBidAskLast._3)
      quote.setHigh(previousBidAskLast._3 * 1.01)
      quote.setLow(previousBidAskLast._3 * 0.99)
      quote.setOpen(previousBidAskLast._3 + (if (random.nextInt() < 0) -1 else 1))
      quote.setTimestamp(ZonedDateTime.now())
      quote.setVolume((random.nextFloat() * 1000000f).toLong)

      lb += quote

      previousBidAskLast = generateQuote(previousBidAskLast._3, previousBidAskLast._3)
    }

    lb.toList
  }

  private def generateQuote(bid: Double, ask: Double): (Double, Double, Double) = {
    val delta =  random.nextFloat() * 2 * (if (random.nextInt() < 0) -1 else 1)
    val last = ((bid + ask) / 2.0) + delta
    (last - 0.02, last + 0.02, last)
  }

}
