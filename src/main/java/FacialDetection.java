import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.request.concept.AddConceptsRequest;
import clarifai2.api.request.input.AddInputsRequest;
import clarifai2.api.request.model.TrainModelRequest;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
                        ClarifaiInput.forImage(ClarifaiImage.of(imageInByte))
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

    public boolean addConcept(Concept c, List<BufferedImage> listInput) throws IOException {
        if (listInput == null || listInput.size() < 10) {
            return false;
        }

        // add concept to model API
        AddConceptsRequest acr = new AddConceptsRequest((BaseClarifaiClient) this.client);
        acr.plus(c);

        // add concept to BI and create ClaifaiInput
        List<ClarifaiInput> listClarifaiInput = new ArrayList<ClarifaiInput>();
        for(BufferedImage bi : listInput) {
            byte[] imageInByte = util.BImageToByte(bi);
            ClarifaiInput cur = ClarifaiInput.forImage(imageInByte);
            cur = cur.withConcepts(c);
            listClarifaiInput.add(cur);
        }

        // add listInput to model API
        AddInputsRequest air = new AddInputsRequest((BaseClarifaiClient) this.client);
        air.plus(listClarifaiInput);

        // retrain model API
        TrainModelRequest tmr = new TrainModelRequest((BaseClarifaiClient) this.client, modelID);
        tmr.executeSync();

        return true;
    }
}
