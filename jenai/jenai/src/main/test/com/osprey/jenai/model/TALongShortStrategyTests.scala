package com.osprey.jenai.model

import java.util

import com.osprey.jenai.model.longshort.TALongShortStrategy
import com.osprey.jenai.quote.FileResourceHistoricalCloseQuoteSource
import com.osprey.math.OspreyQuantMath
import com.osprey.securitymaster.HistoricalQuote
import org.scalactic.TolerantNumerics
import org.scalatest.{FunSuite, Matchers}

import scala.collection.JavaConverters._

/**
  * Created by Goliaeth on 4/12/2017.
  */
class TALongShortStrategyTests extends FunSuite with Matchers {

  test("Initial Strategy Test") {
    val qg = new QuoteGenerator("GDXJ", 35.05, 35.07)
    val evaluator = new AlgoStrategyBacktester(qg.generateHistoricalQuotes(252 * 2), List.empty[List[HistoricalQuote]])
    val strategy: TALongShortStrategy = TALongShortStrategy()
    val result: AlgoStrategyEvaluation = evaluator.test(strategy)

    println(result)
  }


  test("Historical Data Strategy Test") {
    val quoteSource = new FileResourceHistoricalCloseQuoteSource("gdxj_hist.csv", "GDXJ")
    val evaluator = new AlgoStrategyBacktester(quoteSource.read(), List.empty[List[HistoricalQuote]])

    val strategy: TALongShortStrategy = TALongShortStrategy()
    val result: AlgoStrategyEvaluation = evaluator test strategy

    println(result)
    result.signalPairs.foreach(x => println(x))
  }

  test("Quote Generator") {

    val qg = new QuoteGenerator("GDXJ", 35.05, 35.07)

    val quotes = qg.generateHistoricalQuotes(22)
    quotes.foreach(q => println(q))

    assertResult(22) {
      quotes.size
    }

  }

  test(" Scala RSI Calc Test") {

    val quoteSource = new FileResourceHistoricalCloseQuoteSource("gdxj_hist.csv", "GDXJ")
    val quotes: util.List[HistoricalQuote] = new util.ArrayList[HistoricalQuote](quoteSource.read().asJava)

    val rsiJavaCalc1 = OspreyQuantMath.rsiUsingWilders(14, 0, quotes)
    val rsiJavaCalc2 = OspreyQuantMath.rsiUsingWilders(14, 1, quotes)
    val rsiCurveScalaCalc = QuantLib.rsiUsingWilders(14, 0, quotes)

    println(s"java1: $rsiJavaCalc1 |  java2: $rsiJavaCalc2 | scala1: ${rsiCurveScalaCalc.head} | scala2: ${rsiCurveScalaCalc(1)}")

    implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.001)
    assert(rsiJavaCalc1 === rsiCurveScalaCalc.head)
    assert(rsiJavaCalc2 === rsiCurveScalaCalc(1))
  }

  test("File Quote Source") {
    val quoteSource = new FileResourceHistoricalCloseQuoteSource("gdxj_hist.csv", "GDXJ")
    val quotes = quoteSource.read()

    quotes.foreach(q => println(q))

    assertResult(1868) {
      quotes.size
    }
  }

}
