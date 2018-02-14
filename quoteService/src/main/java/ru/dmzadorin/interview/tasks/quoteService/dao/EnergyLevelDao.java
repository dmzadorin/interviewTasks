package ru.dmzadorin.interview.tasks.quoteService.dao;

import ru.dmzadorin.interview.tasks.quoteService.model.EnergyLevel;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public interface EnergyLevelDao {
    Collection<EnergyLevel> getAllEnergyLevels();

    Optional<EnergyLevel> getEnergyLevelByIsin(String isin);

    void updateEnergyLevel(String isin, Double bid, Double ask);
}
