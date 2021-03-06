package com.osprey.math;

public class OspreyJavaMath {

	/**
	 * Calculate the optimal size of a hash map to be initialized to. This
	 * assumes the load factor of the hash map is 0.75
	 * 
	 * @param s
	 *            size of elements to store in the hash map.
	 * @return optimal map size.
	 */
	public static int calcMapInitialSize(int s) {
		return (int) (s / 0.75f) + 1;
	}

	/**
	 * Calculate the optimal size of a hash map to be initialized to.
	 * 
	 * @param s
	 *            Size of elements to store in the hash map.
	 * @param lf
	 *            Load Factor for the hash map.
	 * @return
	 */
	public static int calcMapInitialSize(int s, float lf) {
		return (int) (s / lf) + 1;
	}
}
