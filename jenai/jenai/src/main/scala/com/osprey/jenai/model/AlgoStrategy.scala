package com.osprey.jenai.model

import com.osprey.securitymaster.HistoricalQuote

trait AlgoStrategy {
  def run(previousCloses: List[HistoricalQuote], intraDayQuotes: List[List[HistoricalQuote]], previousTradeSignal: Option[TradeSignal]): Option[TradeSignal]
}
