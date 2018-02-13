package bartosso.gameCore;

import bartosso.IO.Input;
import bartosso.gameCore.entities.Player;
import bartosso.mainHell.display.Display;
import bartosso.util.Time;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

public class Game implements Runnable {

    public static final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final String title = "Hell";
    public static final int CLEAR_COLOR = 0xff6666cc;
    public static final int NUM_BUFFERS = 3;

    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.second/UPDATE_RATE;
    public static final long IDLE_TIME = 1;

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
    private Input input;
    private Level level;
    private Player player;

    public Game(){
        running = false;
        Display.create(width, height, title, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);

        player = new Player(width/2, height/2,1,3);

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
        player.update(input);

    }
    private void render(){
        Display.clear();
        player.render(graphics);
        Display.swapBuffers();

    }
    private void cleanUp(){
        Display.destroy();
        System.out.println("lol");
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
