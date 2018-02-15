package bartosso.gameCore.entities;

import bartosso.IO.Input;
import bartosso.gameCore.Game;
import bartosso.gameCore.level.Level;
import bartosso.graphics.Sprite;
import bartosso.graphics.SpriteSheet;
import bartosso.util.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Player extends Entity {
    private static final int SPRITE_SCALE = 64;


    private float scale;
    private float speed;
    private boolean isMoving;
    private int frame;
    private SpriteSheet spriteSheet;
    private Sprite sprite;
    private int centerX;
    private int centerY;
    private float deltaX;
    private float deltaY;
    private double angle;
    private AffineTransform transform;



    public Player(float x, float y, float scale, float speed) {
        super(EntityType.Player, x, y);

        this.scale = scale;
        this.speed = speed;
        isMoving = false;

        this.spriteSheet = new SpriteSheet(ResourceLoader.loadImage("player.png"),1,SPRITE_SCALE);
        sprite = new Sprite(spriteSheet,scale);




    }

    @Override
    public void update(Input input) {
        deltaX = x;
        deltaY = y;

        if(isMoving) {
            if (!input.getKey(KeyEvent.VK_S)) {
                isMoving = false;
            }
            if (!input.getKey(KeyEvent.VK_W)) {
                isMoving = false;
            }
            if (!input.getKey(KeyEvent.VK_A)) {
                isMoving = false;
            }
            if (!input.getKey(KeyEvent.VK_D)) {
                isMoving = false;
            }
        }
        if (input.getKey(KeyEvent.VK_W)) {
            deltaY -= speed;
            frame++;
            isMoving = true;

        }
        if (input.getKey(KeyEvent.VK_D)) {
            deltaX += speed;
            frame++;
            isMoving = true;

        }
        if (input.getKey(KeyEvent.VK_S)) {
            deltaY += speed;
            frame++;
            isMoving = true;
        }
        if (input.getKey(KeyEvent.VK_A)) {
            deltaX -= speed;
            frame++;
            isMoving = true;
        }

        if (deltaX < 0 | deltaX > (Level.getLevelWidth() - SPRITE_SCALE)) return;
        if (deltaY < 0 | deltaY > (Level.getLevelHeight() - SPRITE_SCALE)) return;
        x = deltaX;
        y = deltaY;



//        System.out.println(MouseInfo.getPointerInfo().getLocation());

    }

    public float getDeltaX(){
        return deltaX;
    }

    public float getDeltaY(){
        return deltaY;
    }

    public int getSpriteScale(){
        return SPRITE_SCALE;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    @Override
    public void render(Graphics2D g) {

        centerX = (int) (x + 32 * scale);
        centerY = (int) (y + 32 * scale);
        angle = Math.atan2(
                centerY - (MouseInfo.getPointerInfo().getLocation().y + 16 ) ,
                centerX - (MouseInfo.getPointerInfo().getLocation().x + 16)) - Math.PI / 2;



        transform = g.getTransform();

        g.rotate(angle, centerX, centerY);

//        sprite.render(g,x,y);

//        g.setTransform(transform);

    }

    public Sprite getSprite(){return sprite;}

    public void setTransform(Graphics2D g){
        g.setTransform(transform);
    }

    public float getSpeed(){
        return speed;
    }
}
