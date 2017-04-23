package com.db.dbgrip.externalsystem;

import com.db.dbgrip.Key;

/**
 * External system that processes keys
 * Created by @dmzadorin on 04.08.16.
 */
public interface ExternalSystem {
    void process(Key key);
}
