package ru.dmzadorin.interview.tasks.quoteService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.dmzadorin.interview.tasks.quoteService.model.EnergyLevel;
import ru.dmzadorin.interview.tasks.quoteService.model.ErrorResponse;
import ru.dmzadorin.interview.tasks.quoteService.model.Quote;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuoteServiceApplication.class)
@WebAppConfiguration
public class QuoteServiceApplicationTest {

    private JacksonTester<Quote> quoteJson;
    private JacksonTester<EnergyLevel> energyLevelJson;
    private JacksonTester<List<EnergyLevel>> energyLevelsJson;
    private JacksonTester<ErrorResponse> errorResponseJson;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void testSaveQuoteAndGetEnergyLevel() throws Exception {
        String isin = "AAAA9999BBBB";
        Quote quote = new Quote(isin, 100.2, 101.9, 2, 0);
        saveQuote(quote);

        verifyEnergyLevel(isin, new EnergyLevel(isin, quote.getBid()));

        quote = new Quote(isin, 101.0, 101.9, 2, 0);
        saveQuote(quote);

        verifyEnergyLevel(isin, new EnergyLevel(isin, quote.getBid()));

        quote = new Quote(isin, 100.0, 100.5, 2, 0);
        saveQuote(quote);

        verifyEnergyLevel(isin, new EnergyLevel(isin, quote.getAsk()));

        isin = "AAAA9999CCCC";
        quote = new Quote(isin, 100.0, 100.5, 2, 0);
        saveQuote(quote);

        verifyEnergyLevel(isin, new EnergyLevel(isin, quote.getBid()));

        List<EnergyLevel> energyLevels = Arrays.asList(
                new EnergyLevel("AAAA9999BBBB", quote.getAsk()),
                new EnergyLevel("AAAA9999CCCC", quote.getBid())
        );
        mvc.perform(get("/quote/getAllEnergyLevels"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(energyLevelsJson.write(energyLevels).getJson())));
    }

    @Test
    public void testQuoteValidation() throws Exception{
        Quote quote = new Quote("123", 100.2, 101.9, 2, 0);
        String jsonContent = errorResponseJson.write(new ErrorResponse("ISIN must be 12 characters in length.")).getJson();
        mvc.perform(
                post("/quote/save")
                        .content(quoteJson.write(quote).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(content().json(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(print());

        quote.setIsin("123456789012");
        quote.setBid(102.0);
        jsonContent = errorResponseJson.write(new ErrorResponse("bid value: " + quote.getBid() +
                " must be less than ask value: " + quote.getAsk())).getJson();
        mvc.perform(
                post("/quote/save")
                        .content(quoteJson.write(quote).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(content().json(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    private void verifyEnergyLevel(String isin, EnergyLevel level) throws Exception {
        mvc.perform(get("/quote/getEnergyLevelByIsin?isin=" + isin))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(energyLevelJson.write(level).getJson())))
                .andDo(print());
    }

    private void saveQuote(Quote quote) throws Exception {
        mvc.perform(
                post("/quote/save")
                        .content(quoteJson.write(quote).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Success")));
    }
}