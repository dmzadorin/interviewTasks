package ru.dmzadorin.interview.tasks.extLib;

import ru.dmzadorin.interview.tasks.extLib.evaluator.ExtLib;

/**
 * Evaluator based on single thread evaluation. Sequentially takes each element
 * of array and runs evaluate method.
 * 
 * @author Dmitry Zadorin
 *
 */
public class SingleThreadEvaluator implements ExtLibEvaluator {
	private ExtLib extLib;

	/**
	 * Creates new instance of single thread evaluator
	 * @param extLib - an ext lib implementation
	 */
	public SingleThreadEvaluator(ExtLib extLib) {
		System.out.println("Creating single thread evaluator");
		this.extLib = extLib;
	}

	/**
	 * Calculates evaluation in sequential single thread mode.
	 */
	@Override
	public double[] evaluate(int[] data, int p) {
		double[] resultArray = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			double result = extLib.eval(data[i], p);
			resultArray[i] = result;
		}
		return resultArray;
	}
}
