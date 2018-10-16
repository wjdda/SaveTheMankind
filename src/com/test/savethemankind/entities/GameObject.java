package com.test.savethemankind.entities;

import com.test.savethemankind.graphics.Sprite;

import java.awt.*;

public abstract class GameObject {
    // TODO Is this pivot?
    protected int    x;
    protected int    y;
    protected int    z;

    protected Sprite sprite;

    // TODO Check body block usability
    /* bodyBlocks[][] bodyBlocks;     * We should decide if we implement object of complex form.
                                      * That is, the object which contains of several block items
                                      * which are not a solid rectangular parallelepiped.
                                      * See ticket "Структура Данных" in Trello board for details. */
    protected int[] size = new int[3];

    protected int hit_points;
    protected int speed;
    protected int armor;                 // 0..100% - percentage damage decrement
    protected int hardness;              /* absolute damage decrement - minimal HP amount that makes some
                                          * damage to the object
                                          * (lower damages are just congested and make no damage) */
    protected int[] res;                 // res[0] is mass, res[1] is energy, res[2} is money etc.

    protected int burn_chance;           // 0..100% - gives the chance of ignition at the bullet hitting.

    protected int explosion_chance_on_hit;      // 0..100% - similar with the previous one
    protected int explosion_chance_on_burn;     /* 0..100% - this is another one. On each calculation step
                                                 * the object can explode if it is burning. */

    protected int movable = 0;

    protected boolean is_broken;
    protected boolean is_burning;
    protected boolean is_dying;

    protected boolean is_moving;

    public GameObject(Sprite sprite, int x, int y, int z) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // For GameObject animations
    public abstract void tick();


    // For rendering graphics of GameObject
    public void render (Graphics g) {
        sprite.render(g, x, y);
    }
}
