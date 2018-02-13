package bartosso.gameCore.entities;

import bartosso.IO.Input;
import bartosso.gameCore.Game;
import bartosso.mainHell.display.Display;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {
    public static final int SPRITE_SCALE = 16;

    private enum Heading {
        NORTH(0 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(6 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(4 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(2 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

        private int x, y, h, w;

        Heading(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }


    }

    private Heading heading;
    private float scale;
    protected float speed;
    protected boolean isMoving;
    private int frame;

    public Player(float x, float y, float scale, float speed) {
        super(EntityType.Player, x, y);

        heading = Heading.NORTH;
        this.scale = scale;
        this.speed = speed;
        isMoving = false;





    }

    @Override
    public void update(Input input) {
        float newX = x;
        float newY = y;

        if(isMoving) {
            if (!input.getKey(KeyEvent.VK_DOWN)) {
                isMoving = false;
            }
            if (!input.getKey(KeyEvent.VK_UP)) {
                isMoving = false;
            }
            if (!input.getKey(KeyEvent.VK_LEFT)) {
                isMoving = false;
            }
            if (!input.getKey(KeyEvent.VK_RIGHT)) {
                isMoving = false;
            }
        }
        if (input.getKey(KeyEvent.VK_UP)) {
            newY -= speed;
            heading = Heading.NORTH;
            frame++;
            isMoving = true;

        }
        if (input.getKey(KeyEvent.VK_RIGHT)) {
            newX += speed;
            heading = Heading.EAST;
            frame++;
            isMoving = true;

        }
        if (input.getKey(KeyEvent.VK_DOWN)) {
            newY += speed;
            heading = Heading.SOUTH;
            frame++;
            isMoving = true;
        }
        if (input.getKey(KeyEvent.VK_LEFT)) {
            newX -= speed;
            heading = Heading.WEST;
            frame++;
            isMoving = true;
        }

        if (newX < 0 | newX > Game.width) return;
        if (newY < 0 | newY > Game.height) return;
        x = newX;
        y = newY;


//        System.out.println(MouseInfo.getPointerInfo().getLocation());

    }

    @Override
    public void render(Graphics2D g) {

        g.fillOval(Math.round(x),Math.round(y),100,100);
    }
}
