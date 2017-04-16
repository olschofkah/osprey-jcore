package com.osprey.jenai.model.longshort

import com.osprey.jenai.model.TACalculator.{calcMacd, calcRsi}
import com.osprey.jenai.model.{AlgoStrategy, TAModel, TradeSignal}
import com.osprey.jenai.quote.QuoteMini
import com.osprey.jenai.trade.TradeType
import com.osprey.jenai.utils.Logging
import com.osprey.screen.criteria.constants.{CrossDirection, RelationalOperator}
import com.osprey.securitymaster.HistoricalQuote

/**
  * Note that this is not a traditional long short strategy.  This long/short means running technical analysis on both directions concurrently
  *
  */
class TALongShortStrategy(val closeLongModel: TAModel,
                          val closeShortModel: TAModel,
                          val intraDayLongModel: TAModel,
                          val intraDayShortModel: TAModel,
                          val longSignalDial: Float,
                          val shortSignalDial: Float) extends AlgoStrategy with Logging {

  override def run(previousCloses: List[HistoricalQuote],
                   intraDayQuotes: List[List[HistoricalQuote]],
                   openTradeSignal: Option[TradeSignal]): Option[TradeSignal] = {
    val longSignalVal: Double = runModel(closeLongModel, intraDayLongModel, previousCloses, intraDayQuotes)
    val shortSignalVal: Double = runModel(closeShortModel, intraDayShortModel, previousCloses, intraDayQuotes)

    // TODO consider stop loss trades
    // TODO switch to using intraday quotes

    val currentQuote = previousCloses.head
    val miniQuote = QuoteMini(currentQuote.getKey.getSymbol,
                               currentQuote.getAdjClose,
                               currentQuote.getAdjClose,
                               currentQuote.getTimestamp.toLocalDateTime)

    val buySignal: Boolean = longSignalVal > longSignalDial && longSignalVal > shortSignalVal
    val sellSignal: Boolean = shortSignalVal > shortSignalDial && longSignalVal < shortSignalVal

    val signal = openTradeSignal match {
      case None if buySignal => Option(TradeSignal(miniQuote, TradeType.BUY, miniQuote.timestamp))
      case None if sellSignal => Option(TradeSignal(miniQuote, TradeType.SELL, miniQuote.timestamp))
      case Some(open) if open.tradeType == TradeType.SELL && buySignal => Option(TradeSignal(miniQuote,
                                                                                              TradeType.BUY,
                                                                                              miniQuote.timestamp))
      case Some(open) if open.tradeType == TradeType.BUY && sellSignal => Option(TradeSignal(miniQuote,
                                                                                              TradeType.SELL,
                                                                                              miniQuote.timestamp))
      case _ => None
    }

    if (signal.nonEmpty) logger
                         .info("Trade signal {} produced for quote {} | Previous Signal {}",
                                Array[Any](signal.get, miniQuote, openTradeSignal))

    signal
  }

  private def runModel(closeModel: TAModel,
                       intraDayModel: TAModel,
                       previousCloses: List[HistoricalQuote],
                       intraDayQuotes: List[List[HistoricalQuote]]): Double = {
    val closeRsi = calcRsi(closeModel.rsi, previousCloses, 0)
    val closeMacd = calcMacd(closeModel.macd, previousCloses)

    // TODO determine what to do with intraday quote history
    val intraDayRsi = if (intraDayQuotes.nonEmpty) calcRsi(intraDayModel.rsi, intraDayQuotes.head, 0) else 0
    val intraDayMacd = if (intraDayQuotes.nonEmpty) calcMacd(intraDayModel.macd, intraDayQuotes.head) else 0

    // @formatter:off
    closeModel.weight *
                (
                  closeModel.weight_RSI * closeRsi
                + closeModel.weight_MACD * closeMacd
                )
    + intraDayModel.weight *
                (
                  intraDayModel.weight_RSI * intraDayRsi
                + intraDayModel.weight_MACD * intraDayMacd
                )
    // @formatter:on
  }

}


object TALongShortStrategy {
  def apply(): TALongShortStrategy = {
    val longModel = TAModel()
    val shortModel = longModel.adjustMacdDirection(CrossDirection.FROM_ABOVE_TO_BELOW)
                     .adjustRsiComparison(65)
                     .adjustRsiRelationalOperator(RelationalOperator._GT)

    new TALongShortStrategy(longModel, shortModel, longModel, shortModel, 0.75f, 0.75f)
  }
}
