package bartosso.gameCore;

import bartosso.IO.Input;
import bartosso.gameCore.entities.Player;
import bartosso.gameCore.level.Level;
import bartosso.mainHell.display.Display;
import bartosso.util.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable {

    public static final int display_Width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int display_Height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final String title     = "Hell";
    public static final int CLEAR_COLOR  = 0xff6666cc;
    public static final int NUM_BUFFERS  = 3;

    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.second/UPDATE_RATE;
    public static final long IDLE_TIME = 1;

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
    private Input input;
    private Level testLevel;
    private Player player;

    public Game(){
        running = false;
        Display.create(display_Width, display_Height, title, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);

        player = new Player(100, 100,1,3);

        testLevel = new Level(display_Height,display_Width,player,0);

    }


    public synchronized void start(){
        if(running)
            return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    public synchronized void stop(){
        if(!running)
            return;

        try {
            running = false;
            gameThread.join(100);
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    private void update(){
        checkEscape();
        testLevel.update(input);
//        player.update(input);

    }
    private void render(){
        Display.clear();
        testLevel.render(graphics);
//        player.render(graphics);
        Display.swapBuffers();

    }
    private void cleanUp(){
        Display.destroy();
    }


    @Override
    public void run() {

        int fps = 0;
        int upd = 0;
        int updL  = 0;

        long count = 0;

        float delta = 0;

        long lastTime = Time.get();
        while(running){
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;
            boolean render = false;

            delta += ( elapsedTime / UPDATE_INTERVAL );
            while(delta>1){
                update();
                upd++;
                delta--;
                if(render){
                    updL++;
                }else{
                    render = true;
                }
            }
            if (render){
                render();
                fps++;
            }else{
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(count >= Time.second){
                Display.setTitle(title + "++ Fps: " + fps + "| Upd:" + upd + "|Updtl:" + updL);
                upd = 0;
                fps = 0;
                updL = 0;
                count = 0;
            }
        }
    }

    private void checkEscape(){
        if (input.getKey(KeyEvent.VK_ESCAPE)) {
            stop();
        }
    }


}
