package com.osprey.jenai.model

import java.time.Duration

import com.osprey.jenai.trade.TradeType

/**
  * Created by Goliaeth on 4/11/2017.
  */
case class TradeSignalPair(openingTrade: TradeSignal, closingTrade: Option[TradeSignal]) {

  lazy val dollarGain: Double = {
    openingTrade.tradeType match {
      case TradeType.BUY if closingTrade.nonEmpty => closingTrade.get.quote.bid - openingTrade.quote.ask
      case TradeType.SELL if closingTrade.nonEmpty => openingTrade.quote.bid - closingTrade.get.quote.ask
      case _ => 0
    }
  }

  lazy val percentGain: Double = {
    openingTrade.tradeType match {
      case TradeType.BUY if closingTrade.nonEmpty => (closingTrade.get.quote.bid - openingTrade.quote.ask) / openingTrade.quote.ask
      case TradeType.SELL if closingTrade.nonEmpty => (openingTrade.quote.bid - closingTrade.get.quote.ask) / closingTrade.get.quote.ask
      case _ => 0
    }
  }

  override def toString(): String = s"TradeSignalPair(dollarGain: $dollarGain, percentGage: $percentGain, holdTime, $holdTime, openingTrade: $openingTrade, closingTrade: $closingTrade"

  /**
    * Trade hold time in days.
    */
  lazy val holdTime: Long = closingTrade match {
    case Some(close) => Duration.between(openingTrade.signalTime, close.signalTime).toDays
    case None => Long.MaxValue
  }
}
