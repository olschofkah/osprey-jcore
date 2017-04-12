package com.osprey.jenai.model

/**
  * Created by Goliaeth on 4/11/2017.
  */
class AlgoStrategyEvaluation(val signalEvals: List[TradeSignalEvaluation]) {

  lazy val totalGain: Double = signalEvals.reduceLeft((a: TradeSignalEvaluation, b: TradeSignalEvaluation) => a.gain + b.gain)
  lazy val averageHoldTimeInMinutes: Long = signalEvals
                                            .reduceLeft((a: TradeSignalEvaluation, b: TradeSignalEvaluation) => a.holdTime + b.holdTime) / signalEvals
                                                                                                                                           .size

}
