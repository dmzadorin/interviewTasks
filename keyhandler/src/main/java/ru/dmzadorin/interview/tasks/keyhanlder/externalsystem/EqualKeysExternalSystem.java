package ru.dmzadorin.interview.tasks.keyhanlder.externalsystem;

import ru.dmzadorin.interview.tasks.keyhanlder.Key;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Simple ExternalSystem implementation, that supports only one equal key processing at time
 * Created by @dmzadorin on 04.08.2016.
 */
public class EqualKeysExternalSystem implements ExternalSystem {
    private final long delayTime;
    private final TimeUnit unit;
    private final Set<Key> currentProcessingKeys;

    public EqualKeysExternalSystem(long delayTime, TimeUnit unit) {
        this.delayTime = delayTime;
        this.unit = unit;
        currentProcessingKeys = ConcurrentHashMap.newKeySet();
    }

    @Override
    public void process(Key key) {
        boolean notExist = currentProcessingKeys.add(key);
        if (!notExist) {
            throw new IllegalArgumentException("Cannot process more than one equal key at the same time!");
        }
        doWork(key);
    }

    private void doWork(Key key) {
        try {
            printMsgWithThreadName(" started processing " + key);
            //Imitating work with provided delay
            unit.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //after finishing work remove the key
        currentProcessingKeys.remove(key);
        printMsgWithThreadName(" finished processing " + key);
    }

    private void printMsgWithThreadName(String msg) {
        String name = Thread.currentThread().getName();
        System.out.println("Thread: " + name + msg);
    }
}
