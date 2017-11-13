import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

import java.awt.image.BufferedImage;
import java.util.List;

public class test {
    public static void main(String[] args) {
        try {
            FacialDetection model = new FacialDetection(/* INSERT API KEY */);
            BufferedImage testImage = util.readImage("src/main/data/Jason1.jpg");
            List<ClarifaiOutput<Prediction>> outputPredictions = model.getPredictions(testImage);
            for(int i = 0; i < outputPredictions.size(); i++) {
                Concept c = model.getBestConcept(outputPredictions.get(i));
                System.out.println(c.name());
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
