package com.test.savethemankind.entities;

import com.test.savethemankind.graphics.Sprite;

public class Unit extends GameObject {
//    int[] direction = new int[2];    // May be best to use 'dx' and 'dy'
    private int dx;
    private int dy;
    private int dz;
    private int detect_radius;

    private Weapon weapon;
    private GameObject targetObject;
//    private int[] targetPoint = new int[2]; // May be best to use 'tx' and 'ty'
    private int tx;
    private int ty;

    private boolean corrupted;

    // TODO Okey, all orders must be in "bool const array", like orders in the MouseController. May be new Class
    private boolean selected;
    private boolean moving;

    public Unit(Sprite sprite, int x, int y, int z) {
        // Initing super.class
        super(sprite, x, y, z);

        // Must initing target as sprite pivot
        tx = x;
        ty = y;

        // And STOP unit :). May be other orders should be here
        moving = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void tick() {
        // TODO Wrap this in the game order "Movement".
        if (moving) {
            move();
        }
    }

    public void move() {
        // This is direction movement of controlled Player in game, like a Diablo
//        x += dx;
//        y += dy;
//        z += dz;

        // Move to target point!
        if (this.x == tx && this.y == ty) {
            this.moving = false;
        } else {
            this.x = tx;
            this.y = ty;
        }
    }

    public GameObject getTarget() {
        return this.targetObject;
    }

    public void setTarget(int x, int y) {
        tx = x;
        ty = y;
        moving = true;
        System.out.println("I go to point: (" + this.tx + ", " + this.ty + ")");
    }

    public void select() {
        if (!selected) {
            System.out.println("I'm selected :) !");
            selected = true;
        }
    }

    public void unselect() {
        if (selected) {
            System.out.println("I'm deselected :( !");
            selected = false;
        }
    }

    public boolean wasSelected() {
        return selected;
    }

    public boolean isMoving() { return moving; }

//    public int[] getTargetPoint() {
//        return this.targetPoint;
//    }
}
