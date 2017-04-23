/**
 * 
 */
package com.andiks.extLibEvaluator.forkJoin;

import java.util.concurrent.ForkJoinPool;

import com.andiks.extLibEvaluator.ExtLibEvaluator;
import com.andiks.extLibEvaluator.PowProblem;
import com.andiks.extLibEvaluator.evaluator.ExtLib;

/**
 * Evaluator based on fork/join framework. Input array is divided into parts
 * recursively, until it reaches the base case - 1000 of elements and then
 * computes them
 * 
 * @author Dmitry Zadorin
 *
 */
public class ForkJoinEvaluator implements ExtLibEvaluator {
	private ForkJoinPool pool;
	private ExtLib extLib;

	/**
	 * Creates new ext lib evaluator implementation based on fork/join framework
	 */
	public ForkJoinEvaluator(ExtLib extLib) {
		int processors = Runtime.getRuntime().availableProcessors();
		pool = new ForkJoinPool(processors);
		this.extLib = extLib;
	}

	/**
	 * Input array is divided into parts recursively, until it reaches the base
	 * case - 1000 of elements and then computes them
	 */
	@Override
	public double[] evaluate(int[] data, int p) {
		int start = 0;
		int end = data.length;
		PowProblem problem = new PowProblem(extLib, data, start, end, p);
		EvaluatorRecursiveTask main = new EvaluatorRecursiveTask(problem);
		return pool.invoke(main);
	}

}
