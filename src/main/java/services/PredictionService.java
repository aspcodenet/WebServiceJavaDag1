package services;

import models.WeatherPrediction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredictionService {
    private static List<WeatherPrediction> allPredictions;

    public PredictionService(){
        if(allPredictions == null)
            allPredictions = new ArrayList<>();
    }

    public List<WeatherPrediction> Search(int dag, int fromHour, int toHour){
        return allPredictions
                .stream()
                .filter(prediction ->  prediction.getPredictionDatum() == dag && prediction.getPredictionHour() >= fromHour && prediction.getPredictionHour() <= toHour )
                .sorted( WeatherPrediction::SORT_HOUR )
                .toList();

    }

    public boolean createNew(WeatherPrediction newPrediction) {
        if(allPredictions.stream().anyMatch(prediction -> prediction.getPredictionDatum() == newPrediction.getPredictionDatum() && prediction.getPredictionHour() == newPrediction.getPredictionHour() && prediction.getApiProvider() == newPrediction.getApiProvider())){
            return false;
        }
        newPrediction.setCreated(new Date());
        newPrediction.setUpdated(new Date());
        allPredictions.add(newPrediction);
        return true;
    }

    public void update(WeatherPrediction t) {
        t.setUpdated(new Date());
    }
}
