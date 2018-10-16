package com.test.savethemankind.input;

public class Key {
    // Creating the keys as simply variables
    public static Key leftClick = new Key();

    /* toggles the keys current state*/
    public void toggle(){
        isDown = !isDown;
    }

    public boolean isDown;
}
