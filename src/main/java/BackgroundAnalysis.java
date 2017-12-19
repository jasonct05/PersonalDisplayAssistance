import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundAnalysis {
    private Color c;
    private int sma;

    public BackgroundAnalysis() {
        // default value for simple moving average is 5
        this(5);
    }

    public BackgroundAnalysis(int sma) {
        this.sma = sma;
        this.c = null;
    }

    public Color getAverageBackgroundColor() {
        return this.c;
    }

    public int getSimpleMovingAverageConstant() {
        return this.sma;
    }

    public Color reanalyzeBackgroundColor(BufferedImage bufferedImage) {
        /* TODO:
            Method will take in a buffered image,
            Find the average rgb per pixel of the bufferedImage input
            simple moving average the color with the class constant c
         */
    }
}
