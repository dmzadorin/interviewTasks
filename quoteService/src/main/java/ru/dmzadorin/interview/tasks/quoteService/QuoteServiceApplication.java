package ru.dmzadorin.interview.tasks.quoteService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.dmzadorin.interview.tasks.quoteService.config.AppConfig;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
@SpringBootApplication
@Import(AppConfig.class)
public class QuoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuoteServiceApplication.class, args);
    }
}
