package com.osprey.jenai.model

import java.time.Duration

import com.osprey.jenai.trade.TradeType

/**
  * Created by Goliaeth on 4/11/2017.
  */
case class TradeSignalEvaluation(openingTrade: TradeSignal, closingTrade: Option[TradeSignal]) {

  lazy val gain: Double = {
    openingTrade.tradeType match {
      case TradeType.BUY if closingTrade.nonEmpty => closingTrade.get.quote.bid - openingTrade.quote.ask
      case TradeType.SELL if closingTrade.nonEmpty => closingTrade.get.quote.ask - openingTrade.quote.bid
      case _ => 0
    }
  }

  /**
    * Trade hold time in minutes.
    */
  lazy val holdTime: Long = closingTrade match {
    case Some(close) => Duration.between(openingTrade.signalTime, close.signalTime).toMinutes
    case None => Long.MaxValue
  }
}
