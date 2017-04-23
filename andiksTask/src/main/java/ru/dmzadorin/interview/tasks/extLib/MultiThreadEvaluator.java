/**
 *
 */
package ru.dmzadorin.interview.tasks.extLib;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import ru.dmzadorin.interview.tasks.extLib.evaluator.ExtLib;

/**
 * Evaluator based on multi thread evaluation. Input array is divided into
 * parts, which are in parallel calculated by separate threads.
 *
 * @author Dmitry Zadorin
 */
public class MultiThreadEvaluator implements ExtLibEvaluator {

    private static final int SLICE_SIZE = 1000;
    private static final int numberOfThreads = 10;
    private ExecutorService executorService;
    private ExtLib extLib;

    /**
     * Creates new instance of multi thread evaluator
     *
     * @param extLib - an ext lib implementation
     */
    public MultiThreadEvaluator(ExtLib extLib) {
        System.out.println("Creating multi thread evaluator");
        executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.extLib = extLib;
    }

    /**
     * Calculates evaluation in parallel mode. Input array is divided into
     * parts, which are in parallel calculated by separate threads.
     */
    @Override
    public double[] evaluate(int[] data, int p) {
        double[] resultArray = new double[data.length];
        List<Future<double[]>> futures = new ArrayList<>();
        int step;

        if (data.length < SLICE_SIZE) {
            step = data.length;
        } else {
            step = data.length / SLICE_SIZE;
        }
        for (int i = 0; i < data.length; i += step) {
            int begin = i;
            int end = i + step;
            PowTask task = new PowTask(extLib, data, begin, end, p);
            Future<double[]> submit = executorService.submit(task);
            futures.add(submit);
        }
        try {
            int next = 0;
            for (Future<double[]> f : futures) {
                double[] temp = f.get();
                System.arraycopy(temp, 0, resultArray, next, temp.length);
                next += step;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute thread", e);
        }
        return resultArray;
    }
}
