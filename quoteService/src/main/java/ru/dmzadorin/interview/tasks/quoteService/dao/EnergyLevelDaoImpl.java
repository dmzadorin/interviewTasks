package ru.dmzadorin.interview.tasks.quoteService.dao;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.dmzadorin.interview.tasks.quoteService.model.EnergyLevel;
import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public class EnergyLevelDaoImpl implements EnergyLevelDao {
    private static final String CREATE_ENERGY_LEVEL = "insert into energy_level (ISIN, VALUE) values (:ISIN, :VALUE)";
    private static final String UPDATE_ENERGY_LEVEL = "update energy_level set VALUE = :VALUE where ISIN = :ISIN";
    private static final String GET_ALL_ENERGY_LEVELS = "select * from energy_level";
    private static final String GET_ENERGY_LEVEL_BY_ISIN = "select * from energy_level where ISIN = :ISIN";
    private final Cache<String, ReadWriteLock> isinLocks = CacheBuilder.newBuilder().weakValues().build();

    private final NamedParameterJdbcTemplate template;

    public EnergyLevelDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Collection<EnergyLevel> getAllEnergyLevels() {
        return template.query(GET_ALL_ENERGY_LEVELS,
                (rs, rowNum) -> new EnergyLevel(rs.getString("ISIN"), rs.getDouble("VALUE")));
    }

    @Override
    public Optional<EnergyLevel> getEnergyLevelByIsin(String isin) {
        return executeActionWithLock(isin, lock -> doGetEnergyLevel(isin, lock));
    }

    @Override
    public void updateEnergyLevel(String isin, Double bid, Double ask) {
        Optional<Double> level = getEnergyLevelByIsin(isin).map(EnergyLevel::getValue);
        Double energyLevel = calculateEnergyLevel(level.orElse(0.0d), bid, ask);
        MapSqlParameterSource paramSource = new MapSqlParameterSource("ISIN", isin);
        paramSource.addValue("VALUE", energyLevel);
        String sql = level.isPresent() ? UPDATE_ENERGY_LEVEL : CREATE_ENERGY_LEVEL;
        executeActionWithLock(isin, lock -> doUpdateEnergyLevel(paramSource, sql, lock));
    }

    private Object doUpdateEnergyLevel(MapSqlParameterSource paramSource, String sql, ReadWriteLock lock) {
        try {
            lock.writeLock().lock();
            template.update(sql, paramSource);
        } finally {
            lock.writeLock().unlock();
        }
        return null;
    }

    private Optional<EnergyLevel> doGetEnergyLevel(String isin, ReadWriteLock lock) {
        try {
            lock.readLock().lock();
            return template.query(
                    GET_ENERGY_LEVEL_BY_ISIN, new MapSqlParameterSource("ISIN", isin),
                    (rs, rn) -> new EnergyLevel(rs.getString("ISIN"), rs.getDouble("VALUE"))
            ).stream().findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }

    private <T> T executeActionWithLock(String isin, Function<ReadWriteLock, T> action) {
        try {
            ReadWriteLock lock = isinLocks.get(isin, ReentrantReadWriteLock::new);
            return action.apply(lock);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    private Double calculateEnergyLevel(Double value, Double bid, Double ask) {
        if (bid != null && bid > value) {
            value = bid;
        } else if (ask != null && ask < value) {
            value = ask;
        }
        return value;
    }
}
