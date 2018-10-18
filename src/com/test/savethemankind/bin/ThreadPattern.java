package com.test.savethemankind.bin;

// FIXME to abstract class
public class ThreadPattern extends Thread {

    protected final String       name;
    protected boolean            SIGNAL_TERM_FROM_PARENT;

    private static ThreadPattern instance = null;

    public static ThreadPattern getInstance() {
        return instance;
    }

    protected ThreadPattern(String threadName) {
        name = threadName;
        SIGNAL_TERM_FROM_PARENT = false;
    }

    @Override @Deprecated
    public void start() {
        throw new UnsupportedOperationException(
                "void start() method with no arguments is forbidden. Use start(int, long) instead."
        );
        // interrupt(); - is it possible that the method start()
        // of the base class anyway started the run() at this moment?
    }

    public boolean start(int attempts, long timeoutMSec) {
        boolean rc = true;
        StackTraceElement [] ste = null;
        while (rc) {
            try {
                Application.printMsg("Thread " + super.getId() + " is starting");
                super.start();
                Application.printMsg("Thread " + super.getId() + " started.");
                rc = false;
            } catch (Exception e) {
                ste = e.getStackTrace();
                Application.timeout(timeoutMSec);
            }
        }
        if (rc) {
            Application.printMsg("Thread " +super.getId() + " failed to start after " + attempts + " attempts.");
            Application.printMsg(ste.toString());
        }
        return rc;
    }

    @Override
    @Deprecated // Derived classes should use repeat() instead.
    public void run() {
        try
        {
            Application.printMsg("Thread " + super.getId() + " is running");
            while (!interrupted() && !SIGNAL_TERM_FROM_PARENT) {
                repeat(); // All the functionality is incapsulated here.
                Application.timeout(1000);
            }
            Application.printMsg("Thread " + super.getId() + " finished.");
        }
        catch (InterruptedException e)
        {
            Application.printMsg("Thread " + super.getId() + " died!");
            e.printStackTrace();
        }
        //super.run(); // Thread.run() is empty, so we don't need it.
    }

    public void repeat() throws InterruptedException {
        // Pure functionality without error handling
        // Is to be used by derived classes
    }

    // TODO: Should we release all mutexes held by a thread before terminating?
    // TODO: What if we call this function when the thread is held by another thread?
    public boolean terminate(long timeoutMsec) throws InterruptedException {
        SIGNAL_TERM_FROM_PARENT = true;
        Application.timeout(timeoutMsec); // wait and hope that the thread finish its work normally

        // still did not exit - it's a pity...
        if (isAlive()) {
            /* Hard termination (See https://stackoverflow.com/questions/671049/how-do-you-kill-a-thread-in-java). */
            interrupt();
            super.interrupt(); // TODO: check of we need this
        } else {
            return false;
        }

        if (isAlive()) { // failed to interrupt
            return true;
        }
        return true;
    }
} // end of class ThreadPattern

