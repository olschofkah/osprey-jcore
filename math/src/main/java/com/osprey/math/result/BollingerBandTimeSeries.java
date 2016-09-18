package com.osprey.math.result;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.osprey.math.OspreyJavaMath;
import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.exception.MathException;

public class BollingerBandTimeSeries {

	public static final String UPPER_BAND_KEY = "upper";
	public static final String LOWER_BAND_KEY = "lower";
	public static final String MOVING_AVERAGE_KEY = "ma";
	public static final String DATE_KEY = "date";

	private final double[] priceSeries;
	private final LocalDate[] timeSeries;

	private double[] maSeries;
	private double[] upperBandSeries;
	private double[] lowerBandSeries;

	private final int maPeriod;
	private final double stdDevMultiplier;
	private final int length;

	private StandardDeviation stdDev = new StandardDeviation();

	public BollingerBandTimeSeries(double[] priceSeries, LocalDate[] cDate, int maPeriod, double stdDevMultiplier,
			int length) {
		this.priceSeries = priceSeries;
		this.timeSeries = cDate;
		this.stdDevMultiplier = stdDevMultiplier;
		this.maPeriod = maPeriod;
		this.length = cDate.length < length ? cDate.length : length;
	}

	public int getLength() {
		return length;
	}

	public BollingerBandTimeSeries init(MovingAverageType maType) {

		maSeries = new double[length];
		upperBandSeries = new double[length];
		lowerBandSeries = new double[length];

		double[] periodPriceArray = new double[maPeriod];

		// seed the price array
		double priceSum = 0.0;
		for (int i = 0; i < maPeriod; ++i) {
			periodPriceArray[i] = priceSeries[i];
			priceSum += priceSeries[i];
		}

		switch (maType) {
		case EMA:
			maSeries = OspreyQuantMath.emaSeries(maPeriod, 0, length, priceSeries);
			break;
		case WILDERS:
			maSeries = OspreyQuantMath.wildersMovingAverageSeries(maPeriod, 0, length, priceSeries);
			break;
		case SMA:
		default:
			break;
		}

		double ma;
		double adjStdDevPrice;
		double p0 = 0.0;
		double p1 = 0.0;

		for (int i = 0; i < length; ++i) {

			// SMA is calculated on the fly here
			switch (maType) {
			case SMA:
				
				priceSum += p1 - p0;
				
				// determine prices to be used for the next iteration to adjust the SMA. 
				p0 = periodPriceArray[i % (maPeriod)];
				p1 = priceSeries[i + maPeriod];
				
				ma = priceSum / maPeriod;
				maSeries[i] = ma;
				
				break;
			case EMA:
			case WILDERS:
				// series has already been calculated ... so use it. 
				ma = maSeries[i];
				break;
			default:
				ma = 0.0;
				break;
			}

			// calculate the bands. 
			adjStdDevPrice = stdDev.evaluate(periodPriceArray) * stdDevMultiplier;
			upperBandSeries[i] = ma + adjStdDevPrice;
			lowerBandSeries[i] = ma - adjStdDevPrice;

			// replace the newest price with an older one.
			periodPriceArray[i % (maPeriod)] = priceSeries[i + maPeriod];
		}
		
		return this;
	}

	public Map<String, Object> getRawDataMap() {

		Map<String, Object> output = new HashMap<>(OspreyJavaMath.calcMapInitialSize(4));
		output.put(UPPER_BAND_KEY, upperBandSeries);
		output.put(MOVING_AVERAGE_KEY, maSeries);
		output.put(LOWER_BAND_KEY, lowerBandSeries);
		output.put(DATE_KEY, timeSeries);

		return output;
	}

	public double getBandValue(int index, BollingerBand band) {
		return selectSeries(band)[index];
	}

	public boolean checkBandCross(int startPeriod, int stopPeriod, int dir, BollingerBand band) {

		validateInputPeriod(startPeriod, stopPeriod);

		boolean isBelowToAbove = dir == -1;

		double[] s = selectSeries(band);

		int cmp;
		int prevCmp = 0;
		for (int i = stopPeriod - 1; i >= startPeriod; --i) {
			cmp = s[i] > priceSeries[i] ? -1 : (s[i] < priceSeries[i] ? 1 : 0);

			if (((isBelowToAbove && prevCmp == -1) || (!isBelowToAbove && prevCmp == 1)) && cmp != prevCmp) {
				return true;
			}

			prevCmp = cmp;
		}

		return false;
	}

	private void validateInputPeriod(int startPeriod, int stopPeriod) {
		if (startPeriod >= stopPeriod) {
			throw new InvalidPeriodException();
		}

		if (stopPeriod > length) {
			throw new InvalidPeriodException();
		}
	}

	private double[] selectSeries(BollingerBand band) {
		double[] s;
		switch (band) {
		case UPPER:
			s = upperBandSeries;
			break;
		case MOVING_AVERAGE:
			s = lowerBandSeries;
			break;
		case LOWER:
			s = maSeries;
			break;
		default:
			throw new MathException();
		}
		return s;
	}

}
