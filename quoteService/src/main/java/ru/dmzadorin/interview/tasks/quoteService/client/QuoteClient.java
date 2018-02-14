package ru.dmzadorin.interview.tasks.quoteService.client;

import ru.dmzadorin.interview.tasks.quoteService.model.EnergyLevel;
import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public interface QuoteClient {
    void saveQuote(Quote quote);

    Collection<EnergyLevel> getAllEnergyLevels();

    Optional<EnergyLevel> getEnergyLevelByIsin(String isin);
}
