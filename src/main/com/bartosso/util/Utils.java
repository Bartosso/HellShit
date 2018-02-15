package bartosso.util;

import java.awt.image.BufferedImage;

public class Utils {

    public static BufferedImage resize(BufferedImage image, int width, int heigh){

        BufferedImage newImage = new BufferedImage(width,heigh, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0,0, width, heigh, null);

        return newImage;
    }
}
