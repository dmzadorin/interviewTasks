package com.andiks.extLibEvaluator;

import java.util.Random;

import com.andiks.extLibEvaluator.forkJoin.ForkJoinEvaluator;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andiks.extLibEvaluator.evaluator.ExtLib;
import com.andiks.extLibEvaluator.evaluator.ExtLibImpl;

/**
 * A set of test cases to measure time performance of several Evaluators
 * TODO rewrite using JMH
 * 
 * @author zadorin
 *
 */
public class ExtLibEvaluatorTest {
	private static final long NANOS_TO_MILISECONDS = 1_000_000;
	private static final int LOAD_SIZE = 100_000_000;
	private static final int MAX_RANDOM = 10_000_000;
	private static final int TEST_RUNS = 10;
	private Random rnd;
	private int power;
	private int loadPower;

	@Before
	public void setUp() throws Exception {
		rnd = new Random();
		power = 5;
		loadPower = 100;
	}

	@Test
	public void testSingleThread() {
		ExtLibFactory<ExtLib> libFactory = ExtLibImpl::new;
		EvaluatorFactory<ExtLibEvaluator> factory = SingleThreadEvaluator::new;
		ExtLibEvaluator evaluator = factory.create(libFactory.createExtLib());
		int[] testDataArray = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double[] testDataArrayExpected = new double[] { 1, 32, 243, 1024, 3125,
				7776, 16807, 32768, 59049, 100000 };
		double[] result = evaluator.evaluate(testDataArray, power);
		Assert.assertEquals(testDataArrayExpected.length, result.length);
		for (int i = 0; i < result.length; i++) {
			Assert.assertEquals(testDataArrayExpected[i], result[i], 1e-10);
		}
	}

	@Test
	public void testSmallArrayInMultiThreads() {
		ExtLibFactory<ExtLib> libFactory = ExtLibImpl::new;
		EvaluatorFactory<ExtLibEvaluator> factory = MultiThreadEvaluator::new;
		ExtLibEvaluator evaluator = factory.create(libFactory.createExtLib());
		int size = 2000;
		int[] testDataArray = new int[size];
		for (int i = 0; i < testDataArray.length; i++) {
			testDataArray[i] = rnd.nextInt(MAX_RANDOM);
		}
		double[] result = evaluator.evaluate(testDataArray, power);
		for (int i = 0; i < testDataArray.length; i++) {
			double expected = Math.pow(testDataArray[i], power);
			Assert.assertEquals(expected, result[i], 1e-10);
		}
		System.out.println("Ok");
	}

	@Test
	public void testLoadSingleThread() {
		System.out.println("Staring single thread test measure, array size: "
				+ LOAD_SIZE + ", test runs: " + TEST_RUNS);
		ExtLibFactory<ExtLib> libFactory = ExtLibImpl::new;
		EvaluatorFactory<ExtLibEvaluator> factory = SingleThreadEvaluator::new;
		ExtLibEvaluator evaluator = factory.create(libFactory.createExtLib());
		Random rnd = new Random();
		int[] loadDataArray;

		long count = 0L;
		for (int i = 0; i < TEST_RUNS; i++) {
			System.out.println("Starting load test iteration: " + i);
			loadDataArray = new int[LOAD_SIZE];
			for (int j = 0; j < loadDataArray.length; j++) {
				loadDataArray[j] = rnd.nextInt(MAX_RANDOM);
			}
			System.out.println("Array filled, start measure");
			long start = System.nanoTime();
			double[] evaluate = evaluator.evaluate(loadDataArray, loadPower);
			long end = System.nanoTime();
			long elapsed = (end - start);
			count += elapsed;
			System.out.println(LOAD_SIZE + " elements power took time: "
					+ elapsed / NANOS_TO_MILISECONDS);
			Assert.assertEquals(LOAD_SIZE, evaluate.length);
			System.gc();
		}
		long avg = count / TEST_RUNS / NANOS_TO_MILISECONDS;
		System.out
				.println("Single thread test measure finished, average time: "
						+ avg);
	}

	@Test
	public void testLoadMultiThreads() {
		System.out.println("Staring multi thread test measure, array size: "
				+ LOAD_SIZE + ", test runs: " + TEST_RUNS);
		ExtLibFactory<ExtLib> libFactory = ExtLibImpl::new;
		EvaluatorFactory<ExtLibEvaluator> factory = MultiThreadEvaluator::new;
		ExtLibEvaluator evaluator = factory.create(libFactory.createExtLib());
		Random rnd = new Random();
		int[] loadDataArray;

		long count = 0L;
		for (int i = 0; i < TEST_RUNS; i++) {
			System.out.println("Starting load test iteration: " + i);
			loadDataArray = new int[LOAD_SIZE];
			for (int j = 0; j < loadDataArray.length; j++) {
				loadDataArray[j] = rnd.nextInt(MAX_RANDOM);
			}
			System.out.println("Array filled, start measure");
			long start = System.nanoTime();
			double[] evaluate = evaluator.evaluate(loadDataArray, loadPower);
			long end = System.nanoTime();
			long elapsed = (end - start);
			count += elapsed;
			System.out.println(LOAD_SIZE + " elements power took time: "
					+ elapsed / NANOS_TO_MILISECONDS);
			Assert.assertEquals(LOAD_SIZE, evaluate.length);
			System.gc();
		}
		long avg = count / TEST_RUNS / NANOS_TO_MILISECONDS;
		System.out.println("Multi thread test measure finished, average time: "
				+ avg);
	}

	@Test
	public void testLoadForkJoin() {
		System.out.println("Staring fork join load test measure, array size: "
				+ LOAD_SIZE + ", test runs: " + TEST_RUNS);
		ExtLibFactory<ExtLib> libFactory = ExtLibImpl::new;
		EvaluatorFactory<ExtLibEvaluator> factory = ForkJoinEvaluator::new;
		ExtLibEvaluator evaluator = factory.create(libFactory.createExtLib());
		Random rnd = new Random();
		int[] loadDataArray;

		long count = 0L;
		for (int i = 0; i < TEST_RUNS; i++) {
			System.out.println("Starting load test iteration: " + i);
			loadDataArray = new int[LOAD_SIZE];
			for (int j = 0; j < loadDataArray.length; j++) {
				loadDataArray[j] = rnd.nextInt(MAX_RANDOM);
			}
			System.out.println("Array filled, start measure");
			long start = System.nanoTime();
			double[] evaluate = evaluator.evaluate(loadDataArray, loadPower);
			long end = System.nanoTime();
			long elapsed = (end - start);
			count += elapsed;
			System.out.println(LOAD_SIZE + " elements power took time: "
					+ elapsed / NANOS_TO_MILISECONDS);
			Assert.assertEquals(LOAD_SIZE, evaluate.length);
			System.gc();
		}
		long avg = count / TEST_RUNS / NANOS_TO_MILISECONDS;
		System.out
				.println("Fork join load test measure finished, average time: "
						+ avg);
	}
}
