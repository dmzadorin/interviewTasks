package ru.dmzadorin.interview.tasks.pyramid.pyramid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.dmzadorin.interview.tasks.pyramid.PyramidTaskApplication;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * Created by @Dmitry Zadorin on 15.02.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PyramidTaskApplication.class)
@WebAppConfiguration
public class PyramidTaskApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetWeight() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/weight?level=0&index=1")).andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"descriptionMessage\":\"Index is greater than level\"}")));
    }

    @Test
    public void testGetWeightPath() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/weight/0/1")).andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"descriptionMessage\":\"Index is greater than level\"}")));
    }
}
