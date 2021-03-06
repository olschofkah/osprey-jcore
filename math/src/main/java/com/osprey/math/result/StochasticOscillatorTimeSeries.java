package com.osprey.math.result;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.osprey.math.OspreyJavaMath;
import com.osprey.math.exception.InvalidPeriodException;

public class StochasticOscillatorTimeSeries {

	public static final String PERCENT_K_KEY = "%K";
	public static final String PERCENT_D_KEY = "%D";
	public static final String DATE_KEY = "date";

	private final double[] curvePctK;
	private final double[] curvePctD;
	private final LocalDate[] curveDate;

	private final int curveLength;

	public StochasticOscillatorTimeSeries(double[] cK, double[] cD, LocalDate[] cDate) {
		this.curvePctK = cK;
		this.curvePctD = cD;
		this.curveDate = cDate;

		curveLength = curveDate.length;
	}

	public int getLength() {
		return curveLength;
	}

	public double getSignalLevel(int period) {
		if (period < curveLength) {
			return curvePctD[period];
		}
		throw new InvalidPeriodException();
	}

	public Map<String, Object> getRawDataMap() {

		Map<String, Object> output = new HashMap<>(OspreyJavaMath.calcMapInitialSize(3));
		output.put(PERCENT_K_KEY, curvePctK);
		output.put(PERCENT_D_KEY, curvePctD);
		output.put(DATE_KEY, curveDate);

		return output;
	}

	public boolean checkLevelCross(int startPeriod, int stopPeriod, int level, int dir) {
		if (startPeriod >= stopPeriod) {
			throw new InvalidPeriodException();
		}

		if (stopPeriod > curveLength) {
			throw new InvalidPeriodException();
		}

		boolean isBelowToAbove = dir == -1;

		int cmp;
		int prevCmp = 0;
		for (int i = startPeriod; i < stopPeriod; ++i) {
			cmp = level > curvePctD[i] ? -1 : (level < curvePctD[i] ? 1 : 0);

			if (((isBelowToAbove && prevCmp == -1) || (!isBelowToAbove && prevCmp == 1)) && cmp != prevCmp) {
				return true;
			}

			prevCmp = cmp;
		}

		return false;
	}

	public boolean checkSignalCross(int startPeriod, int stopPeriod, int dir) {
		if (startPeriod >= stopPeriod) {
			throw new InvalidPeriodException();
		}

		if (stopPeriod > curveLength) {
			throw new InvalidPeriodException();
		}

		boolean isBelowToAbove = dir == -1;

		int cmp;
		int prevCmp = 0;
		for (int i = stopPeriod - 1; i >= startPeriod; --i) {
			cmp = curvePctD[i] > curvePctK[i] ? -1 : (curvePctD[i] < curvePctK[i] ? 1 : 0);

			if (((isBelowToAbove && prevCmp == -1) || (!isBelowToAbove && prevCmp == 1)) && cmp != prevCmp) {
				return true;
			}

			prevCmp = cmp;
		}

		return false;
	}

}
