package com.osprey.jenai.model

/**
  * Created by Goliaeth on 4/11/2017.
  */
object AlgoStrategyEvaluator {

  def evaluate(signals: List[TradeSignal]): AlgoStrategyEvaluation = {

    // TODO look how to turn off run time asserts for 'prod' runs
    assert(signals.size > 2)
    assert(!signals.head.signalTime.isAfter(signals(1).signalTime), _ => "Out of order trade signals")

    val signalEvals: List[TradeSignalEvaluation] = signals.grouped(2).map(tuple => TradeSignalEvaluation(tuple.head, tuple(1))).toList
    new AlgoStrategyEvaluation(signalEvals)
  }
}
