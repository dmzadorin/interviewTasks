package ru.dmzadorin.interview.tasks.pyramid.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.dmzadorin.interview.tasks.pyramid.PyramidTaskApplication;

import static org.junit.Assert.*;

/**
 * Created by @Dmitry Zadorin on 15.02.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PyramidTaskApplication.class)
@WebAppConfiguration
public class CacheWeightCalculatorTest {

    @Autowired
    private WeightCalculator calculator;

    @Test
    public void testGetWeight() throws Exception {
        assertEquals(calculator.getWeight(0, 0), 0, 1e-1);
        assertEquals(calculator.getWeight(-1, 0), 0, 1e-1);
        assertEquals(calculator.getWeight(0, -1), 0, 1e-1);
        assertEquals(calculator.getWeight(1, 2), 0, 1e-1);

        assertEquals(calculator.getWeight(1, 0), 0.5, 1e-1);
        assertEquals(calculator.getWeight(1, 1), 0.5, 1e-1);
        assertEquals(calculator.getWeight(3, 0), 0.875, 1e-3);
        assertEquals(calculator.getWeight(3, 1), 2.125, 1e-3);
        assertEquals(calculator.getWeight(3, 2), 2.125, 1e-3);

        assertEquals(calculator.getWeight(5, 0), 0.96875, 1e-5);
        assertEquals(calculator.getWeight(5, 1), 2.71875, 1e-5);
        assertEquals(calculator.getWeight(5, 2), 3.81250, 1e-5);
        assertEquals(calculator.getWeight(5, 3), 3.81250, 1e-5);
        assertEquals(calculator.getWeight(5, 4), 2.71875, 1e-5);
        assertEquals(calculator.getWeight(5, 5), 0.96875, 1e-5);


    }
}