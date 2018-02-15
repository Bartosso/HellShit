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

    private float offSetX;
    private float offSetY;
    private float oldPlayerX;
    private float oldPlayerY;


    BufferedImage image = ResourceLoader.loadImage("font.jpg");

    public Level(int dimensionHeight, int dimensionWidth, Player player ,int playerSpriteSize) {
      this.dimensionHeight = dimensionHeight;
      this.dimensionWidth  = dimensionWidth;
      this.player = player;
        if (LEVEL_WIDTH > dimensionWidth) {
            offSetX = Math.round(dimensionWidth / 2 - player.getX() - playerSpriteSize);
            offSetX = Math.min(offSetX, 0);
            offSetX = Math.max(offSetX, dimensionWidth - LEVEL_WIDTH);
        }
        if (LEVEL_HEIGHT > dimensionHeight) {
            offSetY = Math.round(dimensionHeight / 2 - player.getY() - playerSpriteSize);
            offSetY = Math.min(offSetY, 0);
            offSetY = Math.max(offSetY, dimensionHeight - LEVEL_HEIGHT);
        }
        oldPlayerX = player.getX();
        oldPlayerY = player.getY();
    }

    public void update(Input input){
        player.update(input);

        if (player.getX()>oldPlayerX){
            if (offSetX + dimensionWidth + ( player.getX() - oldPlayerX) < getLevelWidth()){
                offSetX = offSetX + (player.getX() - oldPlayerX);
                if (offSetX<0) offSetX = 0;
            }
        }

        if (player.getX()<oldPlayerX){
            if (offSetX + dimensionWidth + ( player.getX() - oldPlayerX) > 0){
                offSetX = offSetX + (player.getX() - oldPlayerX);
                if (offSetX<0) offSetX = 0;
            }
        }

        if (player.getY()>oldPlayerY){
            if (offSetY + dimensionHeight + (player.getY() - oldPlayerY) < getLevelHeight()){
                offSetY = offSetY + (player.getY() - oldPlayerY);
                if (offSetY<0) offSetY = 0;
            }
        }

        if (player.getY()<oldPlayerY){
            if (offSetY + dimensionWidth + ( player.getY() - oldPlayerY) > 0){
                offSetY = offSetY + (player.getY() - oldPlayerY);
                if (offSetY<0) offSetY = 0;
            }
        }

        oldPlayerY = player.getY();
        oldPlayerX = player.getX();

    }

    public void render(Graphics2D g){
       g.drawImage(image.getSubimage(Math.round(offSetX),Math.round(offSetY),dimensionWidth,dimensionHeight),0,0,null);
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
