package ru.dmzadorin.interview.tasks.quoteService.client;

import org.springframework.stereotype.Service;
import ru.dmzadorin.interview.tasks.quoteService.dao.EnergyLevelDao;
import ru.dmzadorin.interview.tasks.quoteService.dao.QuoteDao;
import ru.dmzadorin.interview.tasks.quoteService.model.EnergyLevel;
import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
@Service
public class QuoteClientImpl implements QuoteClient {
    private final QuoteDao quoteDao;
    private final EnergyLevelDao energyLevelDao;

    public QuoteClientImpl(QuoteDao quoteDao, EnergyLevelDao energyLevelDao) {
        this.quoteDao = quoteDao;
        this.energyLevelDao = energyLevelDao;
    }

    @Override
    public void saveQuote(Quote quote) {
        quoteDao.saveQuote(quote);
    }

    @Override
    public Collection<EnergyLevel> getAllEnergyLevels() {
        return energyLevelDao.getAllEnergyLevels();
    }

    @Override
    public Optional<EnergyLevel> getEnergyLevelByIsin(String isin) {
        return energyLevelDao.getEnergyLevelByIsin(isin);
    }
}
