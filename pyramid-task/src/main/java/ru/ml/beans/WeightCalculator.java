package ru.ml.beans;

/**
 * Created by @Dmitry Zadorin on 09.02.2016.
 */
public interface WeightCalculator {
    /**
     * Calculates the weight of the block using input level and index.
     * Possible values of level are 0,1,...n, of index 0,1,....n, assuming that index must less or equal to level
     *
     * @param level level param
     * @param index index param
     * @return calculated weight
     */
    double getWeight(int level, int index);

    /**
     * Sets the weight of one block
     *
     * @param blockWeight weight to set
     */
    void setBlockWeight(double blockWeight);
}
