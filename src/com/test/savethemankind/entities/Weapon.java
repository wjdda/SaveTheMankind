package com.test.savethemankind.entities;

import com.test.savethemankind.graphics.Sprite;

class Weapon extends GameObject {
    int caliber;

    // FIXME Is this attribute necessary?
    int type;

    int min_shoot_angle;
    int max_shoot_angle;
    int shoot_speed;
    int shoot_frequency;
    int normal_dmg;
    int armor_dmg;
    int fire_dmg;

    float accuracy;           // Each weapon should have some small deviation percent

    // FIXME May be ammo?
    long fuel;

    long mass;                // the mass of the weapon itself
    long ammo_mass;           // ammo mass

    public Weapon(Sprite sprite, int x, int y, int z) {
        super(sprite, x, y, z);
    }

    @Override
    public void tick() {

    }
}
