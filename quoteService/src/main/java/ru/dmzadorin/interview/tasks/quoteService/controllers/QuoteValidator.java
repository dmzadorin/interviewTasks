package ru.dmzadorin.interview.tasks.quoteService.controllers;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public class QuoteValidator implements Validator {
    private static final int ISIN_SIZE = 12;

    @Override
    public boolean supports(Class<?> clazz) {
        return Quote.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isin", "field.required");
        Quote quote = (Quote) target;
        if (quote.getIsin() != null && quote.getIsin().trim().length() != ISIN_SIZE) {
            errors.rejectValue("isin", "field.min.length",
                    new Object[]{ISIN_SIZE},
                    "ISIN must be " + ISIN_SIZE + " characters in length.");
        }
        Double bid = quote.getBid();
        Double ask = quote.getAsk();
        if (bid != null && ask != null && bid > ask) {
            errors.rejectValue("bid", "field.value",
                    "bid value: " + bid + " must be less than ask value: " + ask);
        }
    }
}
