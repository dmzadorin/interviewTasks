package ru.ml.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import ru.ml.beans.WeightCalculator;
import ru.ml.model.WeightResponse;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

/**
 * Rest controller that handles requests related to weight calculation
 * Created by @Dmitry Zadorin on 09.02.2016.
 */
@RestController
public class PyramidController {
    private static final Logger logger = LogManager.getLogger();
    private static final double BLOCK_WEIGHT = 1.0;
    private static final int MAX_LEVEL = 100_000;

    @Autowired
    private WeightCalculator calculator;

    @Autowired
    private ResourceLoader resourceLoader;

    private int maxLevel;

    private double blockWeight;

    /**
     * Reads app-config.properties and sets runtime values for maxLevel and blockWeight
     */
    @PostConstruct
    public void init() {
        Resource config = resourceLoader.getResource("classpath:app-config.properties");
        Properties props = new Properties();
        try {
            props.load(config.getInputStream());
        } catch (IOException e) {
            logger.error("Failed to load app-config properties", e);
        }
        String maxLevelValue = props.getProperty("maxLevel");
        maxLevel = parseIntValue(maxLevelValue, MAX_LEVEL);
        logger.info("Using max level value: {}", maxLevel);

        String blockWeightValue = props.getProperty("blockWeight");
        blockWeight = parseDoubleValue(blockWeightValue, BLOCK_WEIGHT);
        logger.info("Using block weight value: {}", blockWeight);
        calculator.setBlockWeight(blockWeight);
    }

    @RequestMapping(path = "/weight", method = RequestMethod.GET, produces = "application/json")
    public WeightResponse getWeight(@RequestParam(name = "level") int level, @RequestParam(name = "index") int index, HttpServletRequest request) {
        return calcWeight(level, index);
    }

    @RequestMapping(path = "/weight/{level}/{index}", method = RequestMethod.GET, produces = "application/json")
    public WeightResponse getWeightPath(@PathVariable("level") int level, @PathVariable("index") int index) {
        return calcWeight(level, index);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WeightResponse handleAnyException(Exception ex, HttpServletRequest request) {
        logger.error("Got exception while processing request, method: {}, uri: {}",
                request.getMethod(), request.getRequestURI());
        return WeightResponse.from("Incorrect response: " + ex.getMessage());
    }

    private WeightResponse calcWeight(int level, int index) {
        logger.info("Got new request, level = {}, index = {}", level, index);
        if (level > maxLevel) {
            logger.warn("Trying to set level '{}' greater than maxLevel value '{}'", level, maxLevel);
            level = maxLevel;
        }

        if (level < 0) {
            return WeightResponse.from("Level is less than zero");
        } else if (index < 0) {
            return WeightResponse.from("Index is less than zero");
        } else if (index > level) {
            return WeightResponse.from("Index is greater than level");
        } else {
            double weight = calculator.getWeight(level, index);
            return WeightResponse.from(weight);
        }
    }

    @Scheduled
    private int parseIntValue(String input, int defaultValue) {
        int parsed = defaultValue;
        try {
            parsed = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse int value '{}', message: {}", input, e.getMessage());
        }
        return parsed;
    }

    private double parseDoubleValue(String input, double defaultValue) {
        double parsed = defaultValue;
        try {
            parsed = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse double value '{}', message: {}", input, e.getMessage());
        }
        return parsed;
    }
}
