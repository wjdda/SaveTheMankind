package com.test.savethemankind.bin;

// Singleton
public class V_Thread extends ThreadPattern {

    private static V_Thread instance = new V_Thread("V-Thread");


    public static V_Thread getInstance() {
        return instance;
    }

    private V_Thread(String threadName) {
        super(threadName);
    }

    @Override
    public void repeat() throws InterruptedException {
        Application.printMsg(super.getName() + " is drawing.");
    }
}
