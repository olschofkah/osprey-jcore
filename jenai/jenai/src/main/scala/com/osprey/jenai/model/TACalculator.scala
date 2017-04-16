package com.osprey.jenai.model

import java.{lang, util}

import com.osprey.math.OspreyQuantMath
import com.osprey.screen.criteria.constants.{CrossDirection, RelationalOperator}
import com.osprey.screen.criteria.{MovingAverageConverganceDiverganceCrossoverCriteria, RelativeStrengthIndexCriteria}
import com.osprey.securitymaster.HistoricalQuote

import scala.collection.JavaConverters._

/**
  * Created by Goliaeth on 4/12/2017.
  */
object TACalculator {

  def calcRsi(criteria: RelativeStrengthIndexCriteria, quotes: List[HistoricalQuote], offset: Int): Int = {
    if (offset >= criteria.getPeriodRange) {
      0
    } else if (compare(OspreyQuantMath.rsiUsingWilders(criteria.getPeriod, offset, quotes.asJava),
                       criteria.getRsiComparison,
                       criteria.getRelationalOperator)) {
      1
    } else {
      calcRsi(criteria, quotes, offset + 1)
    }
  }

  def calcMacd(criteria: MovingAverageConverganceDiverganceCrossoverCriteria, quotes: List[HistoricalQuote]): Int = {

    val isAboveToBelow = criteria.getDirection == CrossDirection.FROM_ABOVE_TO_BELOW

    val macdCurves: util.Map[String, util.List[lang.Double]] = OspreyQuantMath
                                                               .macdCurves(criteria.getFastPeriod,
                                                                           criteria.getSlowPeriod,
                                                                           criteria.getSignalPeriod,
                                                                           quotes.asJava)

    val macdCurve: util.List[lang.Double] = macdCurves.get("macd")
    val macdSignalCurve = macdCurves.get("macdSignal")

    return calcMacd(isAboveToBelow, macdCurve, macdSignalCurve, 2 /*unset as -1,0,1 are valid*/ , criteria.getRange)
  }

  def calcMacd(isAboveToBelow: Boolean,
               macdCurve: util.List[lang.Double],
               macdSignalCurve: util.List[lang.Double],
               previousComp: Int,
               offset: Int): Int = {

    if (offset < 0) {
      0
    } else {
      val macd = macdCurve.get(offset)
      val macdSignal = macdSignalCurve.get(offset)
      val comp = if (macd > macdSignal) 1 else if (macd < macdSignal) -1 else 0

      if (previousComp == 2 || !(((isAboveToBelow && previousComp == 1) || (!isAboveToBelow && previousComp == -1)) && previousComp != comp)) {
        calcMacd(isAboveToBelow, macdCurve, macdSignalCurve, comp, offset - 1)
      } else {
        1
      }
    }
  }

  private def compare(a: Double, b: Double, relationalOperator: RelationalOperator): Boolean = {
    relationalOperator match {
      case RelationalOperator._EQ => a == b
      case RelationalOperator._GT => a > b
      case RelationalOperator._LT => a < b
      case _ => ??? // _LE & _GE are not supported here
    }
  }

}
