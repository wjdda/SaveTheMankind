package com.test.savethemankind.graphics;

import com.test.savethemankind.tools.ResourceManager;

import java.awt.image.BufferedImage;

public class SpriteManager extends ResourceManager {
    private BufferedImage image;

    public SpriteManager(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
