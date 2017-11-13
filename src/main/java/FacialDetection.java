import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class FacialDetection {

    private ClarifaiClient client;

    public FacialDetection(String apiKey) {
        this.client =  new ClarifaiBuilder(apiKey).buildSync();
    }

    public List<ClarifaiOutput<Prediction>> getPredictions(BufferedImage b) throws IOException {
        byte[] imageInByte = util.BImageToByte(b);
        return this.client.predict("Facial Detection")
                .withInputs(
                        ClarifaiInput.forImage(ClarifaiImage.of(imageInByte))
                )
                .executeSync().get();
    }

    public static Concept getBestConcept(ClarifaiOutput<Prediction> prediction) {
        Concept bestConcept = null;
        double max = 0;

        List<Prediction> predictionData = prediction.data();
        for(Prediction p:predictionData) {
            Concept c = p.asConcept();
            if (c.value() > max) {
                max = c.value();
                bestConcept = c;
            }
        }
        return bestConcept;
    }
}
