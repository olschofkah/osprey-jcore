package com.osprey.jenai.model

import com.osprey.screen.criteria.constants.{CrossDirection, RelationalOperator}
import com.osprey.screen.criteria.{MovingAverageConverganceDiverganceCrossoverCriteria, RelativeStrengthIndexCriteria}

class TAModel(rawParams: Map[String, Any]) {

  val w_intraDay: Double = rawParams.getOrElse("w_intraDay", 0.0).asInstanceOf[Double]
  val w_close: Double = rawParams.getOrElse("w_close", 1.0).asInstanceOf[Double]

  val w_MACD: Double = rawParams.getOrElse("w_MACD", 0.5).asInstanceOf[Double]
  val w_RSI: Double = rawParams.getOrElse("w_RSI", 0.5).asInstanceOf[Double]
  
  val rsi: RelativeStrengthIndexCriteria =
    rawParams.getOrElse("rsi", new RelativeStrengthIndexCriteria(14, 2, 35, RelationalOperator._LT))
      .asInstanceOf[RelativeStrengthIndexCriteria]

  val macd: MovingAverageConverganceDiverganceCrossoverCriteria =
    rawParams.getOrElse("macd", new MovingAverageConverganceDiverganceCrossoverCriteria(12, 26, 9, 2, CrossDirection.FROM_BELOW_TO_ABOVE))
      .asInstanceOf[MovingAverageConverganceDiverganceCrossoverCriteria]


  def toMap: Map[String, Any] = {
    Map("w_intraDay" -> w_intraDay, "w_close" -> w_close, "w_MACD" -> w_MACD, "w_RSI" -> w_RSI, "rsi" -> rsi, "macd" -> macd)
  }

  // Weight adjustments
  def adjustIntraDayWeighting(weight: Double): TAModel = {
    adjustParameter("w_intraDay" -> weight)
  }

  def adjustCloseWeighting(weight: Double): TAModel = {
    adjustParameter("w_close" -> weight)
  }

  def adjustMacdWeighting(weight: Double): TAModel = {
    adjustParameter("w_MACD" -> weight)
  }

  def adjustRsiWeighting(weight: Double): TAModel = {
    adjustParameter("w_RSI" -> weight)
  }

  // MACD Adjustments
  def adjustMacdFastPeriod(adj: Int): TAModel = {
    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(adj, macd.getSlowPeriod, macd.getSignalPeriod, macd.getRange, macd.getDirection)
    adjustParameter("macd" -> adjMacd)
  }

  def adjustMacdSlowPeriod(adj: Int): TAModel = {
    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod, adj, macd.getSignalPeriod, macd.getRange, macd.getDirection)
    adjustParameter("macd" -> adjMacd)
  }

  def adjustMacdSignalPeriod(adj: Int): TAModel = {
    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod, macd.getSlowPeriod, adj, macd.getRange, macd.getDirection)
    adjustParameter("macd" -> adjMacd)
  }

  def adjustMacdRange(adj: Int): TAModel = {
    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod, macd.getSlowPeriod, macd.getSignalPeriod, adj, macd.getDirection)
    adjustParameter("macd" -> adjMacd)
  }

  def adjustMacdDirection(direction: CrossDirection): TAModel = {
    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod, macd.getSlowPeriod, macd.getSignalPeriod, macd.getRange, direction)
    adjustParameter("macd" -> adjMacd)
  }

  // RSI Adjustments
  def adjustRsiPeriod(adj: Int): TAModel = {
    val adjRsi = new RelativeStrengthIndexCriteria(adj, rsi.getPeriodRange, rsi.getRsiComparison, rsi.getRelationalOperator)
    adjustParameter("rsi" -> adjRsi)
  }

  def adjustRsiRange(adj: Int): TAModel = {
    val adjRsi = new RelativeStrengthIndexCriteria(rsi.getPeriod, adj, rsi.getRsiComparison, rsi.getRelationalOperator)
    adjustParameter("rsi" -> adjRsi)
  }

  def adjustRsiComparison(adj: Int): TAModel = {
    val adjRsi = new RelativeStrengthIndexCriteria(rsi.getPeriod, rsi.getPeriodRange, adj, rsi.getRelationalOperator)
    adjustParameter("rsi" -> adjRsi)
  }

  def adjustRsiRelationalOperator(adj: RelationalOperator): TAModel = {
    val adjRsi = new RelativeStrengthIndexCriteria(rsi.getPeriod, rsi.getPeriodRange, rsi.getRsiComparison, adj)
    adjustParameter("rsi" -> adjRsi)
  }

  // helper methods
  private def adjustParameter(t: (String, Any)): TAModel = {
    new TAModel(rawParams + t)
  }
}

object TAModel {
  def apply(): TAModel = {
    // double create to pick up default mappings of a new model.
    new TAModel(new TAModel(Map.empty[String, Any]).toMap)
  }

  def apply(rawParams: Map[String, Any]): TAModel = {
    // double create to pick up default mappings of a new model.
    new TAModel(rawParams)
  }
}


