package com.osprey.jenai.model

/**
  * Created by Goliaeth on 4/11/2017.
  */
object AlgoStrategyEvaluator {

  def evaluate(signals: List[TradeSignal]): AlgoStrategyEvaluation = {

    // TODO look how to turn off run time asserts for 'prod' runs
    if (signals.size > 1) {
      assert(!signals.head.signalTime.isAfter(signals(1).signalTime), "Out of order trade signals")
    }

    val signalEvals: List[TradeSignalEvaluation] = signals.grouped(2)
                                                   .map((pair: Seq[TradeSignal]) =>
                                                     pair.size match {
                                                       case 0 => null
                                                       case 1 => TradeSignalEvaluation(pair.head, None)
                                                       case 2 => TradeSignalEvaluation(pair.head, Some(pair(1)))
                                                     })
                                                   .filterNot(_ == null)
                                                   .toList

    new AlgoStrategyEvaluation(signalEvals)
  }

}
