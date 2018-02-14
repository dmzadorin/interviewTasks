package ru.dmzadorin.interview.tasks.quoteService.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public class QuoteDaoImpl implements QuoteDao {
    private static final String INSERT_QUOTE =
            "insert into quote (ISIN, BID, ASK, BIDSIZE, ASKSIZE) values (:ISIN, :BID, :ASK, :BIDSIZE, :ASKSIZE)";

    private final NamedParameterJdbcTemplate template;
    private final EnergyLevelDao energyLevelDao;

    public QuoteDaoImpl(NamedParameterJdbcTemplate template, EnergyLevelDao energyLevelDao) {
        this.template = template;
        this.energyLevelDao = energyLevelDao;
    }

    @Override
    public void saveQuote(Quote quote) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("ISIN", quote.getIsin());
        source.addValue("BID", quote.getBid());
        source.addValue("ASK", quote.getAsk());
        source.addValue("BIDSIZE", quote.getBidSize());
        source.addValue("ASKSIZE", quote.getAskSize());
        template.update(INSERT_QUOTE, source);
        energyLevelDao.updateEnergyLevel(quote.getIsin(), quote.getBid(), quote.getAsk());
    }

}
