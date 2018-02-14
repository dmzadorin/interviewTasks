package ru.dmzadorin.interview.tasks.quoteService.dao;

import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public interface QuoteDao {
    void saveQuote(Quote quote);
}
