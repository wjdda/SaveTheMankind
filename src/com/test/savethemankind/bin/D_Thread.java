package com.test.savethemankind.bin;

// Singleton
public class D_Thread extends ThreadPattern {

    private static D_Thread instance = new D_Thread("D-Thread");


    public static D_Thread getInstance() {
        return instance;
    }

    private D_Thread(String threadName) {
        super(threadName);
    }

    @Override
    public void repeat() throws InterruptedException {
        Application.printMsg(super.getName() + " is managing the dashboard.");
    }

}