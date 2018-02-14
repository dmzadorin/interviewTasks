package ru.dmzadorin.interview.tasks.quoteService.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.dmzadorin.interview.tasks.quoteService.client.QuoteClient;
import ru.dmzadorin.interview.tasks.quoteService.model.EnergyLevel;
import ru.dmzadorin.interview.tasks.quoteService.model.ErrorResponse;
import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
@RestController
@RequestMapping("/quote")
public class QuoteController {
    private static final Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    private QuoteClient quoteClient;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new QuoteValidator());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveQuote(@RequestBody @Valid Quote quote) {
        quoteClient.saveQuote(quote);
        return "Success";
    }

    @RequestMapping(value = "/getAllEnergyLevels", method = RequestMethod.GET)
    public Collection<EnergyLevel> getAllEnergyLevels() {
        return quoteClient.getAllEnergyLevels();
    }

    @RequestMapping(value = "/getEnergyLevelByIsin", method = RequestMethod.GET)
    public EnergyLevel getEnergyLevelByIsin(@RequestParam(name = "isin") String isin) {
        return quoteClient.getEnergyLevelByIsin(isin).orElse(null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExcepction(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.error("Got exception while processing request, method: {}, uri: {}",
                request.getMethod(), request.getRequestURI());

        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());

        return new ErrorResponse(errorMsg);
    }
}
