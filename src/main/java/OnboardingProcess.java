import clarifai2.dto.prediction.Concept;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class OnboardingProcess {
    public static final int minimumInput = 10;
    public static final int delay = 500;

    public static boolean addUser(String name) throws IOException, InterruptedException {
        CameraInput ci = new CameraInput();
        BufferedImage[] bis = new BufferedImage[minimumInput];
        for(int i = 0; i < minimumInput; i++) {
            System.out.println("Taking " + i + "/" + minimumInput + " picture...");
            bis[i] = ci.captureImage();
            System.out.println("...");
            Thread.sleep(delay);
        }

        FacialDetection model = new FacialDetection("cf32659fe8f445f8aee00505d5367812");
        Concept c = Concept.forName(name);
        return model.trainNewConcept(c, bis);
    }
}
