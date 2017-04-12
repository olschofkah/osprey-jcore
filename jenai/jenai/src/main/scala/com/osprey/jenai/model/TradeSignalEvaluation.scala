package com.osprey.jenai.model

import java.time.Duration

import com.osprey.jenai.trade.TradeType

/**
  * Created by Goliaeth on 4/11/2017.
  */
case class TradeSignalEvaluation(openingTrade: TradeSignal, closingTrade: TradeSignal) {

  lazy val gain: Double = {
    openingTrade.tradeType match {
      case TradeType.BUY => closingTrade.quote.bid - openingTrade.quote.ask
      case TradeType.SELL => closingTrade.quote.ask - openingTrade.quote.bid
      case _ => ???
    }
  }

  /**
    * Trade hold time in minutes.
    */
  lazy val holdTime: Long = Duration.between(openingTrade.signalTime, closingTrade.signalTime).toMinutes
}
