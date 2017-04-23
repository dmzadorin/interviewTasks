package com.db.dbgrip.keyhandler;

import com.db.dbgrip.Key;

/**
 * Key handler, that prevents simultaneous processing of equal keys.
 * Created by @dmzadorin on 04.08.2016.
 */
public interface KeyHandler {
    void handle(Key key);
}
