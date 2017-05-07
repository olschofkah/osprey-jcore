package com.osprey.jenai.model

import java.util
import java.util.List

import com.osprey.math.OspreyQuantMath
import com.osprey.math.exception.{InsufficientHistoryException, InvalidPeriodException}
import com.osprey.securitymaster.HistoricalQuote
import com.osprey.securitymaster.secondary.{HistoricalGainQuote, HistoricalLossQuote}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Goliaeth on 4/18/2017.
  */
object QuantLib {

  // TODO Use from OspreyQuantLib later
  private val MIN_PERIODS_FOR_AVERAGE: Int = 126

  def rsiUsingWilders(p: Int, offset: Int, prices: List[HistoricalQuote]): Array[Double] = {
    if (p < 2) throw new InvalidPeriodException
    else if (p + offset + MIN_PERIODS_FOR_AVERAGE > prices.size) throw new InsufficientHistoryException
    else {

      val upPeriods = new ArrayBuffer[Double](prices.size)
      val downPeriods = new ArrayBuffer[Double](prices.size)

      val it = prices.iterator()

      var pl = 0.0
      var secondQuote: HistoricalQuote = it.next
      var firstQuote: HistoricalQuote = null
      while(it.hasNext){
        firstQuote = secondQuote
        secondQuote = it.next
        pl = firstQuote.getAdjClose - secondQuote.getAdjClose

        upPeriods += (if (pl > 0) pl else 0.0)
        downPeriods += (if (pl < 0) pl * -1.0 else 0.0)
      }

      // TODO why is this p*2 ?
      val seriesLength = prices.size() - (p * 2) - offset - MIN_PERIODS_FOR_AVERAGE
      val wmaUp = OspreyQuantMath.wildersMovingAverageSeries(p, 0, seriesLength, upPeriods.toArray)
      val wmaDown = OspreyQuantMath.wildersMovingAverageSeries(p, 0, seriesLength, downPeriods.toArray)

      val rsiCurve = new Array[Double](seriesLength)
      0 until seriesLength foreach (i => rsiCurve(i) = 100.0D - 100.0D / (1.0D + wmaUp(i) / wmaDown(i)))

      rsiCurve
    }
  }

}
