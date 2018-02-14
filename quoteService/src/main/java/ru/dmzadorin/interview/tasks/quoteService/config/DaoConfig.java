package ru.dmzadorin.interview.tasks.quoteService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.dmzadorin.interview.tasks.quoteService.dao.EnergyLevelDao;
import ru.dmzadorin.interview.tasks.quoteService.dao.EnergyLevelDaoImpl;
import ru.dmzadorin.interview.tasks.quoteService.dao.QuoteDao;
import ru.dmzadorin.interview.tasks.quoteService.dao.QuoteDaoImpl;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
@Configuration
public class DaoConfig {

    @Bean
    public NamedParameterJdbcTemplate quoteTemplate(@Autowired JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    public EnergyLevelDao energyLevelDao(@Qualifier("quoteTemplate") NamedParameterJdbcTemplate template) {
        return new EnergyLevelDaoImpl(template);
    }

    @Bean
    public QuoteDao quoteDao(@Qualifier("quoteTemplate") NamedParameterJdbcTemplate template, @Autowired EnergyLevelDao energyLevelDao) {
        return new QuoteDaoImpl(template, energyLevelDao);
    }
}
