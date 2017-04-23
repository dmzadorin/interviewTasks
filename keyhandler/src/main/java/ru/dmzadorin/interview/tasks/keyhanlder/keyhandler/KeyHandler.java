package ru.dmzadorin.interview.tasks.keyhanlder.keyhandler;

import ru.dmzadorin.interview.tasks.keyhanlder.Key;

/**
 * Key handler, that prevents simultaneous processing of equal keys.
 * Created by @dmzadorin on 04.08.2016.
 */
public interface KeyHandler {
    void handle(Key key);
}
