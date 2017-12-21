import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int choice = mainMenu(sc);
            switch (choice) {
                case 1:
                    onboard(sc);
                    break;
                case 2:
                    detect();
                    break;
                case 0:
                    sc.close();
                    return;
                default:
                    System.out.println("input is not valid");
            }
        }
    }

    public static int mainMenu(Scanner sc) {
        System.out.println("what would you like to do?");
        System.out.println("1. Onboard a new user");
        System.out.println("2. Facial Detection");
        System.out.println("0. Exit");
        return Integer.parseInt(sc.nextLine());
    }

    public static void onboard(Scanner sc) throws IOException, InterruptedException {
        System.out.println("Please input your name: ");
        String name = sc.nextLine();
        OnboardingProcess.addUser(name);
    }

    public static void detect() throws IOException {
        FacialDetection model = new FacialDetection("cf32659fe8f445f8aee00505d5367812");
        CameraInput ci = new CameraInput();
        System.out.println("Taking your picture ...");
        BufferedImage testImage = ci.captureImage();
        List<ClarifaiOutput<Prediction>> outputPredictions = model.getPredictions(testImage);
        for(int i = 0; i < outputPredictions.size(); i++) {
            Concept c = model.getBestConcept(outputPredictions.get(i));
            System.out.println("Hi " + c.name());
        }
    }
}
