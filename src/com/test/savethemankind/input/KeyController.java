package com.test.savethemankind.input;

import com.test.savethemankind.bin.Application;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class KeyController implements KeyListener {
    public HashMap<Integer, Key> keyBindings = new HashMap<Integer, Key>();
    public static boolean other[] = new boolean[256];
    public Application appClass;

    //Assigning the variable keys to actual letters
    public KeyController(Application app){
        bind(KeyEvent.BUTTON1_DOWN_MASK, Key.leftClick);
        appClass = app;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        other[e.getExtendedKeyCode()] = true;
        keyBindings.get(e.getKeyCode()).isDown = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        other[e.getExtendedKeyCode()] = false;
        keyBindings.get(e.getKeyCode()).isDown = false;
    }

    public boolean isKeyBinded(int extendedKey){
        return keyBindings.containsKey(extendedKey);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    public void bind(Integer keyCode, Key key){
        keyBindings.put(keyCode, key);
    }

    public void releaseAll(){
        for(Key key : keyBindings.values()){
            key.isDown = false;
        }
    }
}
