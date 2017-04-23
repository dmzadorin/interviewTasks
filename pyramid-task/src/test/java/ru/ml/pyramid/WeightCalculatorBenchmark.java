package ru.ml.pyramid;

import org.openjdk.jmh.annotations.*;
import ru.ml.beans.CacheWeightCalculator;
import ru.ml.beans.WeightCalculator;

import java.util.concurrent.TimeUnit;


/**
 * Some performance benchmarks for weight calculator
 *
 * Created by @Dmitry Zadorin on 15.02.2016.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(5)
@State(Scope.Benchmark)
public class WeightCalculatorBenchmark {
    @Param({"100", "200"})
    private int level;

    @Param({"30", "40"})
    private int index;

    private WeightCalculator calculator;

    @Setup
    public void setup() {
        calculator = new CacheWeightCalculator();
    }

    @Benchmark
    public double testWeightCalc() {
        return calculator.getWeight(level, index);
    }
}
