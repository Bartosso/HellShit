package bartosso.gameCore.level;

import bartosso.IO.Input;
import bartosso.gameCore.entities.Player;
import bartosso.util.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Level {

    private static final int LEVEL_WIDTH  = 3000;
    private static final int LEVEL_HEIGHT = 3000;

    private final int dimensionHeight;
    private final int dimensionWidth;
    private final Player player;

    private float oldPlayerX;
    private float oldPlayerY;
    private float playerRenderX;
    private float playerRenderY;
    private float offsetMaxX;
    private float offsetMaxY;
    private float offsetMinX = 0;
    private float offsetMinY = 0;
    private float camX;
    private float camY;


    BufferedImage image = ResourceLoader.loadImage("font.jpg");

    public Level(int dimensionHeight, int dimensionWidth, Player player ,int playerSpriteSize) {
      this.dimensionHeight = dimensionHeight;
      this.dimensionWidth  = dimensionWidth;
      this.player = player;
        oldPlayerX = player.getX();
        oldPlayerY = player.getY();
                offsetMaxX = getLevelWidth()  - dimensionWidth;
                offsetMaxY = getLevelHeight() - dimensionHeight;


        camX = player.getX() - dimensionWidth / 2;
        camY = player.getY() - dimensionHeight / 2;

    }

    public void update(Input input){
        player.update(input);


    }

    public void render(Graphics2D g){
//       g.drawImage(image.getSubimage(Math.round(-camX),Math.round(-camY),dimensionWidth,dimensionHeight),0,0,null);
       player.render(g);



       player.getSprite().render(g,player.getX(),player.getY());
       player.setTransform(g);

    }

    public static int getLevelWidth() {
        return LEVEL_WIDTH;
    }

    public static int getLevelHeight() {
        return LEVEL_HEIGHT;
    }
}
