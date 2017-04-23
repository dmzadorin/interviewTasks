package ru.dmzadorin.interview.tasks.keyhanlder.keyhandler;

import ru.dmzadorin.interview.tasks.keyhanlder.Key;
import ru.dmzadorin.interview.tasks.keyhanlder.externalsystem.EqualKeysExternalSystem;
import ru.dmzadorin.interview.tasks.keyhanlder.externalsystem.ExternalSystem;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by @dmzadorin on 04.08.2016.
 */
public class KeyHandlerTest {
    private int t;

    @BeforeClass
    public void init() {
        t = 8;
    }

    @Test(dataProvider = "keyHandler")
    public void testSimultaneousSingleKeyHandle(KeyHandler handler) throws Exception {
        System.out.println("Started single key handle");

        Key key = new Key(1, "test");
        executeKeyHandler(handler, key);
        System.out.println("Finished single key handle");
    }

    @Test(dataProvider = "keyHandler")
    public void testSimultaneousMultipleKeysHandle(KeyHandler handler) throws Exception {
        System.out.println("Started multiple key handle");
        Key key = new Key(1, "test");
        Key key2 = new Key(2, "test2");
        executeKeyHandler(handler, key, key2);
        System.out.println("Finished multiple key handle");
    }

    private void executeKeyHandler(KeyHandler handler, Key... keys) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(t);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0, j = keys.length; i < t; i++, j++) {
            if (j == keys.length) {
                j = 0;
            }
            Key key = keys[j];
            Future<?> future = service.submit(() -> handler.handle(key));
            futures.add(future);
        }
        for (Future<?> future : futures) {
            //Awaiting termination
            Object o = future.get();
        }
    }

    @DataProvider
    public static Object[][] keyHandler() {
        ExternalSystem system = new EqualKeysExternalSystem(1, TimeUnit.SECONDS);
        return new Object[][]{
                {new LockBasedKeyHandler(system)},
                {new SetBasedKeyHandler(system)},
        };
    }
}