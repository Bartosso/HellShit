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


    private float playerRenderX;
    private float playerRenderY;
    private float offsetMaxX;
    private float offsetMaxY;
    private float camX;
    private float camY;


    private BufferedImage image = ResourceLoader.loadImage("font.jpg");

    public Level(int dimensionHeight, int dimensionWidth, Player player ) {
      this.dimensionHeight = dimensionHeight;
      this.dimensionWidth  = dimensionWidth;
      this.player = player;

      offsetMaxX = getLevelWidth()  - dimensionWidth;
      offsetMaxY = getLevelHeight() - dimensionHeight;


        camX = player.getX() - dimensionWidth / 2;
        camY = player.getY() - dimensionHeight / 2;
    }

    public void update(Input input){
        player.update(input);


    }

    public void render(Graphics2D g){
        updatePlayerRenderPoints();
        checkCamPositions();
        g.drawImage(image.getSubimage(Math.round(camX),Math.round(camY),dimensionWidth,dimensionHeight),0,0,null);
        player.render(g);



       player.getSprite().render(g,playerRenderX,playerRenderY);
       player.setTransform(g);

    }

    private void updatePlayerRenderPoints(){
        if (player.getX()<dimensionWidth/2){
            playerRenderX = player.getX();
            player.setRenderX(playerRenderX);
        }
        if (player.getY()<dimensionHeight/2){
            playerRenderY = player.getY();
            player.setRenderY(playerRenderY);
        }
        if (player.getX()> dimensionWidth/2  && player.getX()<offsetMaxX+dimensionWidth/2){
            playerRenderX = dimensionWidth/2;
            player.setRenderX(playerRenderX);
            camX = camX + player.getDiffX();
        }
        if (player.getX()>offsetMaxX+dimensionWidth/2){
            playerRenderX = player.getX()-offsetMaxX;
            player.setRenderX(playerRenderX);
        }
        if (player.getY()> dimensionHeight/2  && player.getY()<offsetMaxY+dimensionHeight/2){
            playerRenderY = dimensionHeight/2;
            player.setRenderY(playerRenderY);
            camY = camY + player.getDiffY();
        }
        if (player.getY()>offsetMaxY+dimensionHeight/2){
            playerRenderY = player.getY()-offsetMaxY;
            player.setRenderY(playerRenderY);
        }
    }

    private void checkCamPositions(){
        if (camX>offsetMaxX) camX = offsetMaxX;
        if (camY>offsetMaxY) camY = offsetMaxY;
        if (camX < 0)  camX = 0;
        if (camY < 0)  camY = 0;
    }

    public static int getLevelWidth() {
        return LEVEL_WIDTH;
    }

    public static int getLevelHeight() {
        return LEVEL_HEIGHT;
    }
}
