package com.test.savethemankind.entities;

import com.test.savethemankind.graphics.Sprite;

public class Building extends GameObject {
    int energy_consumption;
    int upgrade_time;
    int level;

    boolean is_out_of_energy;

    public Building(Sprite sprite, int x, int y, int z) {
        super(sprite, x, y, z);
    }

    @Override
    public void tick() {

    }
}
