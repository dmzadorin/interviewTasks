package ru.dmzadorin.interview.tasks.quoteService.model;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public class EnergyLevel {
    private final String isin;
    private final double value;

    public EnergyLevel(String isin, double value) {
        this.isin = isin;
        this.value = value;
    }

    public String getIsin() {
        return isin;
    }

    public double getValue() {
        return value;
    }
}
