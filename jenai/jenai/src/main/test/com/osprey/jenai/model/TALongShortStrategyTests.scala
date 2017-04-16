package com.osprey.jenai.model

import com.osprey.jenai.model.longshort.TALongShortStrategy
import com.osprey.securitymaster.HistoricalQuote
import org.scalatest.{FunSuite, Matchers}

/**
  * Created by Goliaeth on 4/12/2017.
  */
class TALongShortStrategyTests extends FunSuite with Matchers {

  test("Initial Strategy Test") {
    val strat = TALongShortStrategy

    val qg = new QuoteGenerator("GDXJ", 35.05, 35.07)

    val evaluator = new AlgoStrategyBacktester(qg.generateHistoricalQuotes(252 * 2), List.empty[List[HistoricalQuote]])

    val strategy: TALongShortStrategy = TALongShortStrategy()

    val result: AlgoStrategyEvaluation = evaluator.test(strategy)

    println(result)
  }

  test("Quote Generator") {

    val qg = new QuoteGenerator("GDXJ", 35.05, 35.07)

    val quotes = qg.generateHistoricalQuotes(22)
    quotes.foreach(q => println(q))

    assertResult(22) {
      quotes.size
    }

  }

}
