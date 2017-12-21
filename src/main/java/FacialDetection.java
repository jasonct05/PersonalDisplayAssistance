import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.request.model.Action;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class FacialDetection {
    public static final String modelID = "Facial Detection";

    private ClarifaiClient client;

    public FacialDetection(String apiKey) {
        this.client =  new ClarifaiBuilder(apiKey).buildSync();
    }

    public List<ClarifaiOutput<Prediction>> getPredictions(BufferedImage b) throws IOException {
        byte[] imageInByte = util.BImageToByte(b);
        return this.client.predict("Facial Detection")
                .withInputs(
                        ClarifaiInput.forImage(imageInByte)
                )
                .executeSync().get();
    }

    public Concept getBestConcept(ClarifaiOutput<Prediction> prediction) {
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

    public boolean trainNewConcept(Concept c, BufferedImage[] listInput) throws IOException {
        if (listInput == null || listInput.length < OnboardingProcess.minimumInput) {
            return false;
        }

        // add concept to model API
        final ConceptModel model = client.getModelByID(modelID).executeSync().get().asConceptModel();
        model.modify()
                .withConcepts(Action.MERGE, c)
                .executeSync();

        // add concept to BI and create ClaifaiInput
        for(BufferedImage bi : listInput) {
            byte[] imageInByte = util.BImageToByte(bi);
            this.client.addInputs()
                    .plus(
                            ClarifaiInput.forImage(imageInByte).withConcepts(c)
                    )
                    .executeSync();
        }

        this.client.trainModel(modelID).executeSync();

        return true;
    }
}
