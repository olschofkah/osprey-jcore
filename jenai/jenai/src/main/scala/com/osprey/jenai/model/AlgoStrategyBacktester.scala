package com.osprey.jenai.model

import com.osprey.securitymaster.HistoricalQuote

/**
  * Created by Goliaeth on 4/10/2017.
  */
class AlgoStrategyBacktester(val previousCloses: Array[HistoricalQuote], val intraDayQuotes: Array[Array[HistoricalQuote]]) {


  def test(strategy: AlgoStrategy): AlgoStrategyEvaluation = {

    // TODO Make Async
    val signals: Seq[TradeSignal] = 0 to 252 flatMap { i =>
      strategy.test(previousCloses.drop(i), intraDayQuotes.drop(i))
    }

    AlgoStrategyEvaluator.evaluate(signals.toList)
  }
}
