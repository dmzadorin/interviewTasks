package ru.dmzadorin.interview.tasks.quoteService.model;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public class Quote {
    private String isin;
    private Double bid;
    private Double ask;
    private int bidSize;
    private int askSize;

    public Quote(){}

    public Quote(String isin, Double bid, Double ask, int bidSize, int askSize) {
        this.isin = isin;
        this.bid = bid;
        this.ask = ask;
        this.bidSize = bidSize;
        this.askSize = askSize;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public int getBidSize() {
        return bidSize;
    }

    public void setBidSize(int bidSize) {
        this.bidSize = bidSize;
    }

    public int getAskSize() {
        return askSize;
    }

    public void setAskSize(int askSize) {
        this.askSize = askSize;
    }
}
