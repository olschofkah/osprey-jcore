package com.osprey.jenai.model.longshort

import com.osprey.jenai.model.{AlgoStrategy, TAModel, TradeSignal}
import com.osprey.math.OspreyQuantMath
import com.osprey.screen.criteria.RelativeStrengthIndexCriteria
import com.osprey.screen.criteria.constants.{CrossDirection, RelationalOperator}
import com.osprey.securitymaster.HistoricalQuote

import scala.collection.JavaConverters._

/**
  * Note that this is not a traditional long short strategy.  This long/short means running technical analysis on both directions concurrently
  *
  * @param longModel
  * @param shortModel
  */
class TALongShortStrategy(val longModel: TAModel, val shortModel: TAModel) extends AlgoStrategy {
  override def run(previousCloses: List[HistoricalQuote],
                   intraDayQuotes: List[List[HistoricalQuote]],
                   previousTradeSignal: Option[TradeSignal]): Option[TradeSignal] = {
    val longSignalVal: Double = runModel(longModel, previousCloses, intraDayQuotes)
    val shortSignalVal: Double = runModel(shortModel, previousCloses, intraDayQuotes)
    None
  }

  private def runModel(m: TAModel, previousCloses: List[HistoricalQuote], intraDayQuotes: List[List[HistoricalQuote]]): Double = {
    // @formatter:off
    longModel.w_close *
                (longModel.w_RSI * runRsi(longModel.rsi, previousCloses, 0)
                + longModel.w_MACD * (1))
    + longModel.w_intraDay *
                (longModel.w_RSI * runRsi(longModel.rsi, previousCloses, 0)
                + longModel.w_MACD * (1))
    // @formatter:on
  }


  private def runRsi(criteria: RelativeStrengthIndexCriteria, quotes: List[HistoricalQuote], offset: Int): Int = {
    if (offset >= criteria.getPeriodRange) {
      0
    } else if (compare(OspreyQuantMath.rsiUsingWilders(criteria.getPeriod, offset, quotes.asJava),
                       criteria.getRsiComparison,
                       criteria.getRelationalOperator)) {
      1
    } else {
      runRsi(criteria, quotes, offset - 1)
    }
  }


  private def compare(rsi: Double, rsiComp: Double, relationalOperator: RelationalOperator): Boolean = {
    relationalOperator match {
      case RelationalOperator._EQ => rsi == rsiComp
      case RelationalOperator._GT => rsi > rsiComp
      case RelationalOperator._LT => rsi < rsiComp
      case _ => ??? // _LE & _GE are not supported here
    }
  }
}


object TALongShortStrategy {
  def default(): TALongShortStrategy = {
    val longModel = TAModel()
    val shortModel = longModel.adjustMacdDirection(CrossDirection.FROM_ABOVE_TO_BELOW)
                     .adjustRsiComparison(65)
                     .adjustRsiRelationalOperator(RelationalOperator._GT)

    new TALongShortStrategy(longModel, shortModel)
  }
}
