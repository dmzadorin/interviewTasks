/**
 * 
 */
package com.andiks.extLibEvaluator;

import com.andiks.extLibEvaluator.evaluator.ExtLib;

/**
 * A pow problem that need's to be computed. Each problem has a link to whole
 * data array, but it takes only values from start to end indexes.
 * 
 * @author Dmitry Zadorin
 *
 */
public class PowProblem {
	private ExtLib extLib;
	private int power;
	private int start;
	private int end;
	private int[] data;

	/**
	 * Creates a new pow problem.
	 * 
	 * @param extLib
	 *            - ext lib implementation, which will be used to calculate
	 *            power
	 * @param data
	 *            - an array of elements
	 * @param start
	 *            - start index from array
	 * @param end
	 *            - end index from array
	 * @param power
	 *            - power value
	 */
	public PowProblem(ExtLib extLib, int[] data, int start, int end, int power) {
		this.extLib = extLib;
		this.data = data;
		this.start = start;
		this.end = end;
		this.power = power;
	}

	/**
	 * Computes power value for an array elements from start to end
	 * 
	 * @return an array of computed values
	 */
	public double[] compute() {
		double[] part = new double[end - start];
		for (int i = start, j = 0; i < end; i++, j++) {
			double val = extLib.eval(getData()[i], getPower());
			part[j] = val;
		}
		return part;
	}

	/**
	 * Returns ext lib, which is used to calculate power
	 * 
	 * @return ext lib
	 */
	public ExtLib getExtLib() {
		return extLib;
	}

	/**
	 * Returns start index of part array
	 * 
	 * @return start index
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Returns end index of part array
	 * 
	 * @return end index
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Returns initial array of elements to compute
	 * 
	 * @return - initial array
	 */
	public int[] getData() {
		return data;
	}

	/**
	 * Returns the powe value
	 * 
	 * @return power value
	 */
	public int getPower() {
		return power;
	}
}
