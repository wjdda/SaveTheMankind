package com.test.savethemankind.entities;

public class GameMap {
    private static volatile GameMap instance;

    GameObject[][] blocks;

    int x = 0;
    int y = 0;

    int nature = 0;

    int through_walkable = 0;
    int through_shootable = 0;
    int on_buildable = 0;


    // https://habr.com/post/27108/
    public static GameMap getInstance() {
        if (instance == null) {
            synchronized (GameMap.class) {
                if (instance == null)
                    instance = new GameMap();
            }
        }
        return instance;
    }
}
