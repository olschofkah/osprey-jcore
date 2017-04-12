package com.osprey.jenai.model

import java.time.LocalDateTime

import com.osprey.jenai.quote.QuoteMini
import com.osprey.jenai.trade.TradeType

/**
  * Created by Goliaeth on 4/10/2017.
  */
case class TradeSignal(quote: QuoteMini, tradeType: TradeType.Value, signalTime: LocalDateTime) {
  val symbol: String = quote.symbol
}