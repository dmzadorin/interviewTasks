package ru.dmzadorin.interview.tasks.keyhanlder.externalsystem;

import ru.dmzadorin.interview.tasks.keyhanlder.Key;

/**
 * External system that processes keys
 * Created by @dmzadorin on 04.08.16.
 */
public interface ExternalSystem {
    void process(Key key);
}
