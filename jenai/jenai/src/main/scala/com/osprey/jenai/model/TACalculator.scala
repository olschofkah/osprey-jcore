package com.osprey.jenai.model

import java.{lang, util}

import com.osprey.math.OspreyQuantMath
import com.osprey.screen.criteria.constants.{CrossDirection, RelationalOperator, ScreenType}
import com.osprey.screen.criteria.{MovingAverageConverganceDiverganceCrossoverCriteria, MovingAverageConverganceDiverganceLevelCriteria, RelativeStrengthIndexCriteria}
import com.osprey.securitymaster.HistoricalQuote
import org.apache.logging.log4j.LogManager

import scala.collection.JavaConverters._

/**
  * Created by Goliaeth on 4/12/2017.
  */
object TACalculator {

  lazy val logger = LogManager.getLogger(this.getClass);

  def calcRsi(criteria: RelativeStrengthIndexCriteria, quotes: List[HistoricalQuote], offset: Int): Int = {
    if (offset >= criteria.getPeriodRange) {
      0
    } else if (compare(OspreyQuantMath.rsiUsingWilders(criteria.getPeriod, offset, quotes.asJava),
                        criteria.getRsiComparison,
                        criteria.getRelationalOperator,
                        ScreenType.RSI)) {
      1
    } else {
      calcRsi(criteria, quotes, offset + 1)
    }
  }


  def calcMacdLevel(criteria: MovingAverageConverganceDiverganceLevelCriteria, quotes: List[HistoricalQuote]): Int = {

    val macd = OspreyQuantMath.macdCurves(criteria.getFastPeriod, criteria.getSlowPeriod, criteria.getSignalPeriod, quotes.asJava)
    calcMacdLevel(criteria.getLevel, macd.get("macdSignal"), criteria.getRange, criteria.getRelationalOperator)

  }

  private def calcMacdLevel(level: Double, macdSignal: util.List[lang.Double], offset: Int, operator: RelationalOperator): Int = {
    if (offset < 0) {
      0
    } else if (compare(macdSignal.get(offset), level, operator, ScreenType.MACD_LEVEL)) {
      1
    } else {
      calcMacdLevel(level, macdSignal, offset - 1, operator)
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

  private def calcMacd(isAboveToBelow: Boolean,
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
        //  logger.debug("Successful cross for {} ", () => ScreenType.MACD)
        1
      }
    }
  }

  private def compare(actual: Double, target: Double, relationalOperator: RelationalOperator, callingType: ScreenType): Boolean = {
    val comp = relationalOperator match {
      case RelationalOperator._EQ => actual == target
      case RelationalOperator._GT => actual > target
      case RelationalOperator._LT => actual < target
      case _ => ??? // _LE & _GE are not supported here
    }

    // if (comp) logger.debug(s"Successful comparison for $callingType | actual $actual | target $target")

    comp
  }

}
