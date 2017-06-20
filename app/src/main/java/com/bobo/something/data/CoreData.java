package com.bobo.something.data;

/**
 * Created by huangbo on 2017/6/20.
 */

public class CoreData {
    private static final CoreData ourInstance = new CoreData();

    public static CoreData getInstance() {
        return ourInstance;
    }

    private CoreData() {
    }
}
