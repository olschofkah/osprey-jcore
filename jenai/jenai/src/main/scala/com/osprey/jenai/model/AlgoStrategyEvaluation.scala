package com.osprey.jenai.model

/**
  * Created by Goliaeth on 4/11/2017.
  */
class AlgoStrategyEvaluation(val signalEvals: List[TradeSignalEvaluation]) {

  lazy val totalGain: Double = signalEvals.map(s => s.gain).sum
  lazy val averageHoldTimeInMinutes: Double = signalEvals.map(s => s.holdTime).sum / signalEvals.size

}
