package ru.ml.beans;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.ml.model.WeightEntry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cached version of weight calculator. Uses ConcurrentHashMap as a cache in order to memorize the results of previous calls
 * <p>
 * Created by @Dmitry Zadorin on 15.02.2016.
 */
@Service
public class CacheWeightCalculator implements WeightCalculator {
    private static final Logger logger = LogManager.getLogger();
    private static final double DEFAULT_BLOCK_WEIGHT = 1.0;
    private static final Map<WeightEntry, Double> WEIGHT_CACHE = new ConcurrentHashMap<>();

    private double blockWeight;

    public CacheWeightCalculator() {
        blockWeight = DEFAULT_BLOCK_WEIGHT;
    }

    @Override
    public double getWeight(int level, int index) {
        logger.debug("Calculating weight for level: {}, index: {}", level, index);
        return calcWeightRecursive(level, index);
    }

    @Override
    public void setBlockWeight(double blockWeight) {
        this.blockWeight = blockWeight;
    }

    private double calcWeightRecursive(int level, int index) {
        WeightEntry entry = new WeightEntry(level, index);
        Double result = WEIGHT_CACHE.get(entry);
        if (result != null) {
            logger.debug("Got value for level '{}' and index '{}' from cache: {}", level, index, result);
            return result;
        } else {
            double weightRecursive = calcWeight(level, index);
            WEIGHT_CACHE.putIfAbsent(entry, weightRecursive);
            return weightRecursive;
        }
    }

    /**
     * Calculates weight using level and index param.
     *
     * If level is 0, then weight is 0
     * If level is 1, then weight is calculated as block weight divided by two
     * If index is 0, then weight is calculated recursively with level = level - 1
     * If index is equal to level, then weight is calculated recursively with level = level - 1, index = index - 1
     * in other cases, weight is calculated recursively with level = level - 1, index = index - 1 and
     * level = level - 1, index = index
     *
     * @param level level param
     * @param index index param
     * @return calculated weight
     */
    private double calcWeight(int level, int index) {
        if (level <= 0 || index < 0 || index > level) {
            return 0.0;
        } else if (level == 1) {
            return blockWeight / 2;
        } else {
            double weight;
            if (index == 0) {
                weight = (calcWeightRecursive(level - 1, 0) + blockWeight) / 2;
            } else if (index == level) {
                weight = (calcWeightRecursive(level - 1, index - 1) + blockWeight) / 2;
            } else {
                weight = (calcWeightRecursive(level - 1, index - 1) + blockWeight) / 2
                        + (calcWeightRecursive(level - 1, index) + blockWeight) / 2;
            }
            return weight;
        }
    }
}
