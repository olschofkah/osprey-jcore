package com.osprey.jenai.model

/**
  * Created by Goliaeth on 4/11/2017.
  */
class AlgoStrategyEvaluation(val signalPairs: Set[TradeSignalPair]) {

  lazy val totalDollarGain: Double = signalPairs.map(s => s.dollarGain).sum
  // TODO total percent gain
  lazy val averageHoldTimeInMinutes: Double = if (signalPairs.nonEmpty) signalPairs.map(s => s.holdTime)
                                                                        .sum / signalPairs.size else 0

  override def toString = s"AlgoStrategyEvaluation(totalDollarGain=$totalDollarGain, averageHoldTimeInMinutes=$averageHoldTimeInMinutes, signalEvals=$signalPairs"
}
