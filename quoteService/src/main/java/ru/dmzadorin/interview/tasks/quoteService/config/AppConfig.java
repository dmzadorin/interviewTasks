package ru.dmzadorin.interview.tasks.quoteService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.dmzadorin.interview.tasks.quoteService.client.QuoteClient;
import ru.dmzadorin.interview.tasks.quoteService.client.QuoteClientImpl;
import ru.dmzadorin.interview.tasks.quoteService.dao.EnergyLevelDao;
import ru.dmzadorin.interview.tasks.quoteService.dao.QuoteDao;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
@Configuration
@Import(DaoConfig.class)
public class AppConfig {

    @Bean
    public QuoteClient quoteClient(@Autowired QuoteDao quoteDao, @Autowired EnergyLevelDao energyLevelDao) {
        return new QuoteClientImpl(quoteDao, energyLevelDao);
    }
}
