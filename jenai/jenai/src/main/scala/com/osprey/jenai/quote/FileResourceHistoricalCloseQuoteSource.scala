package com.osprey.jenai.quote

import java.time.{LocalDate, ZonedDateTime}

import com.osprey.securitymaster.HistoricalQuote
import org.apache.commons.lang3.StringUtils

import scala.io.{BufferedSource, Source}

/**
  * Created by Goliaeth on 4/16/2017.
  */
class FileResourceHistoricalCloseQuoteSource(val fileName: String, val symbol: String) {

  /**
    * Date,Open,High,Low,Close,Volume,Adj Close
    */
  def read(): List[HistoricalQuote] = {
    val source: BufferedSource = Source.fromResource(fileName)

    try {
      val quotes: Iterator[HistoricalQuote] = source.getLines()
                                              .map((line: String) => line.split(",").map(a => StringUtils.strip(a)))
                                              .drop(1) // header row
                                              .map(record => {
        val quote = new HistoricalQuote(symbol, LocalDate.parse(record(0)))
        quote.setAdjClose(record(6).toDouble)
        quote.setClose(record(4).toDouble)
        quote.setHigh(record(2).toDouble)
        quote.setLow(record(3).toDouble)
        quote.setOpen(record(1).toDouble)
        quote.setTimestamp(ZonedDateTime.now())
        quote.setVolume(record(5).toLong)
        quote
      })

      quotes.toList
    }
    finally {
      source.close
    }
  }
}
