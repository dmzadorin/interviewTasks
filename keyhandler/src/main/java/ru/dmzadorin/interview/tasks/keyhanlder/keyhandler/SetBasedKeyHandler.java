package ru.dmzadorin.interview.tasks.keyhanlder.keyhandler;

import ru.dmzadorin.interview.tasks.keyhanlder.Key;
import ru.dmzadorin.interview.tasks.keyhanlder.externalsystem.ExternalSystem;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Key handler, that prevents simultaneous processing of equal keys.
 * This implementation is based on using wait\notify calls.
 * When some thread executes handle method, it searches for key in set.
 * If the key is already in set, then current thread calls wait on key, causing current thread to release the monitor.
 * When each key is processed by external system, key is removed from the set and notify is called over the key.
 * Such technique allows to process equal keys sequentially.
 * <p>
 * Created by @dmzadorin on 04.08.16.
 */
public class SetBasedKeyHandler implements KeyHandler {
    private final ExternalSystem externalSystem;
    private final Set<Key> keySet;

    public SetBasedKeyHandler(ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
        this.keySet = ConcurrentHashMap.newKeySet();
    }

    public void handle(Key key) {
        boolean keyNotExist = keySet.add(key);
        synchronized (key) {
            while (!keyNotExist) {
                try {
                    key.wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                keyNotExist = keySet.add(key);
            }
        }
        try {
            externalSystem.process(key);
        } finally {
            keySet.remove(key);
            synchronized (key) {
                key.notify();
            }
        }
    }

    @Override
    public String toString() {
        return "Set based KeyHandler";
    }
}
