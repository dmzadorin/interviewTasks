package ru.dmzadorin.interview.tasks.keyhanlder.keyhandler;

import ru.dmzadorin.interview.tasks.keyhanlder.Key;
import ru.dmzadorin.interview.tasks.keyhanlder.externalsystem.ExternalSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Key handler, that prevents simultaneous processing of equal keys.
 * This implementation is based on using locks. Each input key has associated lock.
 * When some thread executes handle method, LockBasedKeyHandler searches for lock, and tries to aquire it.
 * If some other thread had already acquired the lock, then current thread blocks until the lock will be released.
 * Such technique allows to process equal keys sequentially.
 * <p>
 * Created by @dmzadorin on 04.08.16.
 */
public class LockBasedKeyHandler implements KeyHandler {
    private final ExternalSystem externalSystem;
    private final Map<Key, Lock> keyLockMap;

    public LockBasedKeyHandler(ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
        this.keyLockMap = new ConcurrentHashMap<>();
    }

    @Override
    public void handle(Key key) {
        try {
            doBeforeProcessing(key);
            externalSystem.process(key);
        } finally {
            doAfterProcessing(key);
        }
    }

    private void doBeforeProcessing(Key key) {
        Lock value = new ReentrantLock();
        printMsgWithThreadName(" searching for lock to the " + key);
        //putting new reentrant lock if there is no such one
        Lock lock = keyLockMap.putIfAbsent(key, value);
        if (lock == null) {
            //Luckily there is no lock in the map, so we are the firts.
            printMsgWithThreadName(". There is no lock for the " + key + ", creating new one");
            lock = value;
        }
        printMsgWithThreadName(" trying to acquire lock for: " + key);
        lock.lock();
        printMsgWithThreadName(" acquired lock for: " + key);
    }

    private void doAfterProcessing(Key key) {
        Lock lock = keyLockMap.get(key);
        printMsgWithThreadName(" releasing lock for: " + key);
        lock.unlock();
    }

    private void printMsgWithThreadName(String msg) {
        String name = Thread.currentThread().getName();
        System.out.println("Thread: " + name + msg);
    }

    @Override
    public String toString() {
        return "Lock based KeyHandler";
    }
}
