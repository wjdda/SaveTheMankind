// https://habr.com/post/145433/
package com.test.savethemankind.graphics;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sprite {
    private final static Map<String, SpriteManager> spriteMap = new HashMap<String, SpriteManager>();
    private final String fileName;
    private SpriteManager spriteManager;

    public Sprite(String fileName) {
        this.fileName = fileName;
        
        SpriteManager oldSprite = spriteMap.get(fileName);
        if (oldSprite != null) {
            this.spriteManager = oldSprite;
            this.spriteManager.addReference();
        } else {
            try {
                this.spriteManager = new SpriteManager(
                        ImageIO.read(new File("./res/drawable/" + fileName))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //FIXME альтернатива java.lang.ref.Cleaner или PhantomReference
    @Override
    protected void finalize() throws Throwable {
        if (spriteManager.removeReference() && !fileName.isEmpty()) {
            spriteMap.remove(fileName);
        }
        super.finalize();
    }

    public int getWidth() {
        return spriteManager.getImage().getWidth();
    }

    public int getHeight() {
        return spriteManager.getImage().getHeight();
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(spriteManager.getImage(), x, y,null);
    }
}
