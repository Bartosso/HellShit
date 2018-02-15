package bartosso.mainHell.display;

import bartosso.IO.Input;
import bartosso.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Display {

    private static boolean created = false;
    private static JFrame window;
    private static Canvas content;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearcolor;
    private static BufferStrategy bufferStrategy;


    public static void create(int width, int height, String title, int _clearcolor, int numBuffers) {

        if (created) return;

        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new Canvas();

        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setVisible(true);

        content.setPreferredSize(new Dimension(width,height));

        window.setResizable(false);
        window.getContentPane().add(content);
        window.pack();
        window.setLocationRelativeTo(null);


        buffer = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearcolor = _clearcolor;
        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();
        created = true;



        // Cursor image.
        BufferedImage cursorImg = ResourceLoader.loadImage("cursor.png");



        // Create a new blank cursor.
        Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "custom cursor");

        // Set the blank cursor to the JFrame.
        window.getContentPane().setCursor(myCursor);




    }

    public static void clear() {
        Arrays.fill(bufferData, clearcolor);}


    public static void swapBuffers() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer,0,0, null);
        bufferStrategy.show();
    }
    public static Graphics2D getGraphics() {return  ((Graphics2D) bufferGraphics);}
    public static void destroy() {if(!created) return;
        window.dispose();}

    public static void setTitle(String title){
        window.setTitle(title);
    }

    public static void addInputListener(Input inputListener){
        window.add(inputListener);
    }



}
