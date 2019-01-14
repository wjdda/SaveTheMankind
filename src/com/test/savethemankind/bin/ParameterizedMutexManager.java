package com.test.savethemankind.bin;

import java.util.concurrent.Semaphore;

// TODO: May be rename this to MutexDict
public class ParameterizedMutexManager extends MutexManager<String, Semaphore> {

    public static ParameterizedMutexManager instance = null;
    public static ParameterizedMutexManager getInstance() {
        if (instance == null) return new ParameterizedMutexManager();
        return instance;
    }
    public ParameterizedMutexManager() {
        // must be private, but otherwise I cannot inherit in Main.
        super();
    }
} // we must derive from a generic type to use a full power of getGenericSuperclass() (avoiding type erasure)