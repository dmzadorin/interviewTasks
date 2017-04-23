package ru.dmzadorin.interview.tasks.extLib;

/**
 * Represents entry point for data evaluation. 
 * @author Dmitry Zadorin
 */
public interface ExtLibEvaluator {
	/**
	 * Processes input array, powering each number in data array to the power p
	 * @param data - input data to be calculated
	 * @param p - power value
	 * @return an array of powered values
	 */
	double[] evaluate(int[] data, int p);
}
