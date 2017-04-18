package com.osprey.jenai.model

import com.osprey.jenai.utils.Logging
import com.osprey.securitymaster.HistoricalQuote

import scala.collection.immutable
import scala.collection.mutable.ListBuffer

/**
  * Created by Goliaeth on 4/10/2017.
  */
class AlgoStrategyBacktester(previousCloses: List[HistoricalQuote],
                             intraDayQuotes: List[List[HistoricalQuote]]) extends Logging {


  def test(strategy: AlgoStrategy): AlgoStrategyEvaluation = {

    // TODO Make Async
    val signals: immutable.Seq[TradeSignal] = 0 to 252 flatMap { i =>
      strategy.run(previousCloses.drop(i), intraDayQuotes.drop(i), Option.empty)
    }

    if (logger.isInfoEnabled) {
      println("Signals ... ")
      signals.foreach(x => logger.info(x))
    }

    val filteredSignals = filterSequence(signals.reverse)

    if (logger.isInfoEnabled) {
      println("Filtered Signals ... ")
      filteredSignals.foreach(x => logger.info(x))
    }

    AlgoStrategyEvaluator.evaluate(filteredSignals)
  }

  /**
    * Filter the signals down to a sequence that is tradeable
    *
    * @param signals
    * @return
    */
  private def filterSequence(signals: immutable.Seq[TradeSignal]): List[TradeSignal] = {

    if (signals.size == 1) {
      List[TradeSignal](signals.head)
    } else {
      val lb = new ListBuffer[TradeSignal]()
      var openSignal: TradeSignal = null

      for (signal <- signals) {
        if (openSignal == null || (openSignal != null && openSignal.tradeType != signal.tradeType)) {
          lb += signal
          openSignal = if (openSignal == null) signal else null
        }
      }

      lb.toList
    }
  }

}
