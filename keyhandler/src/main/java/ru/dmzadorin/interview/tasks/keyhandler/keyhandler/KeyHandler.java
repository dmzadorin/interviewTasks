package ru.dmzadorin.interview.tasks.keyhandler.keyhandler;

import ru.dmzadorin.interview.tasks.keyhandler.Key;

/**
 * Key handler, that prevents simultaneous processing of equal keys.
 * Created by @dmzadorin on 04.08.2016.
 */
public interface KeyHandler {
    void handle(Key key);
}
