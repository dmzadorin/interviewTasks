package ru.dmzadorin.interview.tasks.pyramid.model;

import java.util.Objects;

/**
 * Structure that represents composite key of weight element. Consists of two sub keys: level and index, both positive
 * WeightEntry is used as a key in CacheWeightCalculator.
 * <p>
 * Created by @Dmitry Zadorin on 15.02.2016.
 */
public class WeightEntry {
    private final int level;
    private final int index;

    /**
     * Creates new instance of WeightEntry
     *
     * @param level level value
     * @param index index value
     */
    public WeightEntry(int level, int index) {
        this.level = level;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightEntry that = (WeightEntry) o;
        return level == that.level && index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, index);
    }

    @Override
    public String toString() {
        return "level=" + level + ", index=" + index;
    }
}
