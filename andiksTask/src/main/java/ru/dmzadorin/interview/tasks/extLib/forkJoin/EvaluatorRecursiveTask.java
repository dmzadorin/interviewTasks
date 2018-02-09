/**
 * 
 */
package ru.dmzadorin.interview.tasks.extLib.forkJoin;

import ru.dmzadorin.interview.tasks.extLib.PowProblem;

import java.util.concurrent.RecursiveTask;

/**
 * A recursive task for data evaluation. Input array is divided into parts
 * recursively, until it reaches the base case - 1000 of elements and then
 * computes them
 * 
 * @author Dmitry Zadorin
 *
 */
public class EvaluatorRecursiveTask extends RecursiveTask<double[]> {

	private static final long serialVersionUID = 2228401718847566736L;
	private static final int THRESHOLD = 1000;
	private PowProblem problem;

	/**
	 * Creates new recursive task
	 * 
	 * @param problem
	 *            - a pow problem to compute
	 */
	public EvaluatorRecursiveTask(PowProblem problem) {
		this.problem = problem;
	}

	/**
	 * Computes evaluator task recursively. If problem data size is less or
	 * equal THRESHOLD than, problem is computed. Else problem data is divided in
	 * two equal parts.
	 */
	@Override
	protected double[] compute() {
		int start = problem.getStart();
		int end = problem.getEnd();
		double[] result;
		if (end - start <= THRESHOLD) {
			result = problem.compute();
		} else {
			int median = (end - start) / 2 + start;
			EvaluatorRecursiveTask task = new EvaluatorRecursiveTask(
					new PowProblem(problem.getExtLib(), problem.getData(),
							problem.getStart(), median, problem.getPower()));
			EvaluatorRecursiveTask task2 = new EvaluatorRecursiveTask(
					new PowProblem(problem.getExtLib(), problem.getData(),
							median, end, problem.getPower()));
			task.fork();
			double[] compute2 = task2.compute();
			double[] compute = task.join();
			double[] resultArray = new double[compute.length + compute2.length];
			System.arraycopy(compute, 0, resultArray, 0, compute.length);
			int startIdx = compute.length;
			System.arraycopy(compute2, 0, resultArray, startIdx, compute2.length);
			result = resultArray;
		}
		return result;
	}
}
