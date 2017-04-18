package com.osprey.jenai.model

import com.osprey.screen.criteria.constants.{CrossDirection, RelationalOperator}
import com.osprey.screen.criteria.{MovingAverageConverganceDiverganceCrossoverCriteria, MovingAverageConverganceDiverganceLevelCriteria, RelativeStrengthIndexCriteria}

class TAModel(rawParams: Map[String, Any]) {

  val weight: Double = rawParams.getOrElse("weight", 0.5).asInstanceOf[Double]

  val weight_MACD: Double = rawParams.getOrElse("weight_MACD", 0.25).asInstanceOf[Double]
  val weight_MACD_LEVEL: Double = rawParams.getOrElse("weight_MACD_LEVEL", 0.25).asInstanceOf[Double]
  val weight_RSI: Double = rawParams.getOrElse("weight_RSI", 0.5).asInstanceOf[Double]

  val rsi: RelativeStrengthIndexCriteria =
    rawParams.getOrElse("rsi", new RelativeStrengthIndexCriteria(14, 2, 35, RelationalOperator._LT))
    .asInstanceOf[RelativeStrengthIndexCriteria]

  val macd: MovingAverageConverganceDiverganceCrossoverCriteria =
    rawParams.getOrElse("macd", new MovingAverageConverganceDiverganceCrossoverCriteria(12, 26, 9, 2, CrossDirection.FROM_BELOW_TO_ABOVE))
    .asInstanceOf[MovingAverageConverganceDiverganceCrossoverCriteria]

  val macdLevel: MovingAverageConverganceDiverganceLevelCriteria =
    rawParams.getOrElse("macdLevel", new MovingAverageConverganceDiverganceLevelCriteria(12, 26, 9, 2, -0.5, RelationalOperator._LT))
    .asInstanceOf[MovingAverageConverganceDiverganceLevelCriteria]


  def toMap: Map[String, Any] = {
    Map("weight" -> weight,
         "weight_MACD" -> weight_MACD,
         "weight_MACD_LEVEL" -> weight_MACD_LEVEL,
         "weight_RSI" -> weight_RSI,
         "rsi" -> rsi,
         "macd" -> macd,
         "macdLevel" -> macdLevel)
  }

  // Weight adjustments
  def adjustIntraDayWeighting(weight: Double): TAModel = {
    adjustParameter("w_intraDay" -> weight)
  }

  def adjustCloseWeighting(weight: Double): TAModel = {
    adjustParameter("w_close" -> weight)
  }

  def adjustMacdWeighting(weight: Double): TAModel = {
    adjustParameter("weight_MACD" -> weight)
  }

  def adjustRsiWeighting(weight: Double): TAModel = {
    adjustParameter("weight_RSI" -> weight)
  }

  // MACD Adjustments
  def adjustMacdFastPeriod(adj: Int): TAModel = {
    val adjMacdLevel = new MovingAverageConverganceDiverganceLevelCriteria(adj,
                                                                            macdLevel.getSlowPeriod,
                                                                            macdLevel.getSignalPeriod,
                                                                            macdLevel.getRange,
                                                                            macdLevel.getLevel,
                                                                            macdLevel.getRelationalOperator)

    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(adj,
                                                                           macd.getSlowPeriod,
                                                                           macd.getSignalPeriod,
                                                                           macd.getRange,
                                                                           macd.getDirection)
    adjustParameter("macd" -> adjMacd, "macdLevel" -> adjMacdLevel)
  }

  def adjustMacdSlowPeriod(adj: Int): TAModel = {
    val adjMacdLevel = new MovingAverageConverganceDiverganceLevelCriteria(macdLevel.getFastPeriod,
                                                                            adj,
                                                                            macdLevel.getSignalPeriod,
                                                                            macdLevel.getRange,
                                                                            macdLevel.getLevel,
                                                                            macdLevel.getRelationalOperator)

    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod,
                                                                           adj,
                                                                           macd.getSignalPeriod,
                                                                           macd.getRange,
                                                                           macd.getDirection)
    adjustParameter("macd" -> adjMacd, "macdLevel" -> adjMacdLevel)
  }

  def adjustMacdSignalPeriod(adj: Int): TAModel = {
    val adjMacdLevel = new MovingAverageConverganceDiverganceLevelCriteria(macdLevel.getFastPeriod,
                                                                            macdLevel.getSlowPeriod,
                                                                            adj,
                                                                            macdLevel.getRange,
                                                                            macdLevel.getLevel,
                                                                            macdLevel.getRelationalOperator)

    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod,
                                                                           macd.getSlowPeriod,
                                                                           adj,
                                                                           macd.getRange,
                                                                           macd.getDirection)
    adjustParameter("macd" -> adjMacd, "macdLevel" -> adjMacdLevel)
  }

  def adjustMacdRange(adj: Int): TAModel = {
    val adjMacdLevel = new MovingAverageConverganceDiverganceLevelCriteria(macdLevel.getFastPeriod,
                                                                            macdLevel.getSlowPeriod,
                                                                            macdLevel.getSignalPeriod,
                                                                            adj,
                                                                            macdLevel.getLevel,
                                                                            macdLevel.getRelationalOperator)

    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod,
                                                                           macd.getSlowPeriod,
                                                                           macd.getSignalPeriod,
                                                                           adj,
                                                                           macd.getDirection)
    adjustParameter("macd" -> adjMacd, "macdLevel" -> adjMacdLevel)
  }

  def adjustMacdLevel(adj: Double): TAModel = {
    val adjMacdLevel = new MovingAverageConverganceDiverganceLevelCriteria(macdLevel.getFastPeriod,
                                                                            macdLevel.getSlowPeriod,
                                                                            macdLevel.getSignalPeriod,
                                                                            macdLevel.getRange,
                                                                            adj,
                                                                            macdLevel.getRelationalOperator)

    adjustParameter("macdLevel" -> adjMacdLevel)
  }

  def adjustMacdLevelRelationalOperator(adj: RelationalOperator): TAModel = {
    val adjMacdLevel = new MovingAverageConverganceDiverganceLevelCriteria(macdLevel.getFastPeriod,
                                                                            macdLevel.getSlowPeriod,
                                                                            macdLevel.getSignalPeriod,
                                                                            macdLevel.getRange,
                                                                            macdLevel.getLevel,
                                                                            adj)

    adjustParameter("macdLevel" -> adjMacdLevel)
  }

  def adjustMacdDirection(direction: CrossDirection): TAModel = {
    val adjMacd = new MovingAverageConverganceDiverganceCrossoverCriteria(macd.getFastPeriod,
                                                                           macd.getSlowPeriod,
                                                                           macd.getSignalPeriod,
                                                                           macd.getRange,
                                                                           direction)
    adjustParameter("macd" -> adjMacd)
  }

  // RSI Adjustments
  def adjustRsiPeriod(adj: Int): TAModel = {
    val adjRsi = new RelativeStrengthIndexCriteria(adj,
                                                    rsi.getPeriodRange,
                                                    rsi.getRsiComparison,
                                                    rsi.getRelationalOperator)
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
  private def adjustParameter(t: (String, Any)*): TAModel = {
    new TAModel(rawParams ++ t)
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



