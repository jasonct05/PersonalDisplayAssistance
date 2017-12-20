import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;


public class CameraInput {
    private Dimension[] nonStandardResolutions;
    private Webcam webcam;

    public CameraInput() {
        this(2000, 1000, 1000, 500);
    }

    public CameraInput(int pWidth, int pHeight, int hWidth, int hHeight) {
        this.nonStandardResolutions = new Dimension[] {
                WebcamResolution.PAL.getSize(),
                WebcamResolution.HD720.getSize(),
                new Dimension(pWidth, pHeight),
                new Dimension(hWidth, hHeight),
        };

        this.webcam = Webcam.getDefault();
        this.webcam.setCustomViewSizes(this.nonStandardResolutions);
        this.webcam.setViewSize(WebcamResolution.HD720.getSize());
    }

    public BufferedImage captureImage() throws IOException {
        this.webcam.open();
        BufferedImage image = this.webcam.getImage();
        this.webcam.close();
        return image;
    }
}