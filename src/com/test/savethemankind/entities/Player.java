package com.test.savethemankind.entities;

import com.test.savethemankind.entities.Building;
import com.test.savethemankind.entities.Unit;
import com.test.savethemankind.input.MouseController;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This class can using to create players in the game, include computer opponents
 */

// TODO If we not releasing controller for Enemy, than this class must be a child and static
public class Player extends Faction {
    private String nickname;

    // Player Controllers
    private MouseController mouseController;

    // TODO Move other fields to Fanction class?
//    int race;       // I think best solution create the Faction class. And inherit Player and others.
    int id;         // We should distinguish all players by id. Computer should always have id=0

//    String name;

//    Resource[] resources;
    Building[] buildings;
    private Unit[] units;

    boolean defeated;


    public Player(String playerName, Unit[] units) {
        nickname = playerName;

        // TODO Is this initialization should be in this class?
        mouseController = new MouseController();
        mouseController.turnOn();

        // TODO Check the correctness of this copying
        this.units = units;
    }

    public String getNickname() {
        return nickname;
    }

    public MouseController getMouseController() {
        return mouseController;
    }

    public void disablingControllers() {
        System.out.println("Player " + nickname + " controllers disconnected");
        mouseController.turnOff();
    }

    public void enablingControllers() {
        System.out.println("Player " + nickname + " controllers connected");
        mouseController.turnOn();
    }

    // FIXME Release targetExists, ERR_DOES_NOT_EXIST, ERR_IMPEDIMENT, ERR_FAR_AWAY, ERR_IMPEDIMENT
//    void requestAttackElement(GameObject e) {
//        if (!targetExists(e))
//            return ERR_DOES_NOT_EXIST;
//
//        switch (targetShootable(x,y,z,weapon)) {
//            case ERR_IMPEDIMENT: {
//                return ERR_IMPEDIMENT;
//            }
//            case ERR_FAR_AWAY: {
//                return ERR_IMPEDIMENT;
//            }
//            // TODO Add more cases
//        }
//    }

    // FIXME Move this to Unit class
//    void requestAttackPoint(int x, int y) {
//        this.targetPoint[0] = x;
//        this.targetPoint[1] = y;
//    }

    // FIXME Move this to Unit class
//    void stopAttack() {
//        this.targetObject = null;
//        this.targetPoint[0] = -1;
//        this.targetPoint[1] = -1;
//    }

    // FIXME Move this to Unit class
    void shoot(int x, int y, int z) {

    }

    // TODO I think we must release Controls here
    void requestMoveTo(int x, int y, int z) {

    }

    public void tick() {
        // If Player has no controller - nothing to do
        if (mouseController.isDisabled()) return;

        // Controller handling
        if (mouseController.wasPressed(MouseEvent.BUTTON1)) {
            System.out.println(
                    "Mister " + nickname + " pressed " + MouseEvent.BUTTON1 +
                    " in position: (" + mouseController.getX() + ", " + mouseController.getY()+ ")"
            );

            for (Unit unit: this.units) {
                if (!unit.wasSelected()) {
                    System.out.println(
                            "Checking unit in position: (" + unit.x + ", " + unit.y + ")"
                    );

                    // Selecting Unit
                    // If click in Unit Sprite. I get square 128x128 under unit
                    // TODO Of course this big dicks must be changed by good code
                    if (
                            (mouseController.getX() <= unit.x + 32) &&
                            (mouseController.getX() >= unit.x - 32) &&
                            (mouseController.getY() <= unit.y + 51) &&
                            (mouseController.getY() >= unit.y - 51)
                    ) {
                            unit.select();
                            break;
                    } else {
                        System.out.println("Mister " + nickname + " not found a unit");
                    }

                } else {
                    // Set a target for Unit to start moving
                    // If click in Unit Sprite (SpriteSize 64x64, Pivot on (x-32, x+32), (y-32, y+32))
                    // TODO Of course this big dicks must be changed by good code
                    if (
                            (mouseController.getX() >= unit.x + 32) ||
                            (mouseController.getX() <= unit.x - 32) ||
                            (mouseController.getY() >= unit.y + 51) ||
                            (mouseController.getY() <= unit.y - 51)
                    ) {
                            System.out.println("Setting unit target position");
                            unit.setTarget(mouseController.getX(), mouseController.getY());
                            break;
                    } else {
                        System.out.println("Mister " + nickname + " ordered unit to move himself");
                    }
                }
            }
        }

        if (mouseController.wasPressed(MouseEvent.BUTTON3)) {
            System.out.println("Mister " + nickname + " pressed " + MouseEvent.BUTTON3);
            for (Unit unit: this.units) {
                // Selecting Unit
                if (unit.wasSelected()) {
                    unit.unselect();
                }
            }
        }

        for (Unit unit: this.units) {
            unit.tick();
        }
    }

    public void render(Graphics g) {
        for (Unit unit: this.units) {
            unit.render(g);
        }
    }
}
