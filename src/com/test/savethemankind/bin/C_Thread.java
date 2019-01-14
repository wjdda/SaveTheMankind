package com.test.savethemankind.bin;

// Singleton
public class C_Thread extends ThreadPattern {

    private static C_Thread instance = new C_Thread("C-Thread");


    public static C_Thread getInstance() {
        return instance;
    }

    private C_Thread(String threadName) {
        super(threadName);
    }

    @Override
    public void repeat() throws InterruptedException {
        Application.printMsg(super.getName() + " is calculating.");
    }
}
