package se.systementor.webservicejavadag1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import se.systementor.webservicejavadag1.models.WeatherPrediction;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredictionService {
    private static List<WeatherPrediction> allPredictions;

    public PredictionService(){
        if(allPredictions == null) {
            try {
                allPredictions = readAllFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        newPrediction.setCreated(LocalDateTime.now());
        newPrediction.setUpdated(LocalDateTime.now());
        allPredictions.add(newPrediction);
        try {
            writeAllToFile(allPredictions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void update(WeatherPrediction t) {
        t.setUpdated(LocalDateTime.now());
        try {
            writeAllToFile(allPredictions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private List<WeatherPrediction> readAllFromFile() throws IOException {
        if(!Files.exists(Path.of("predictions.json"))) return new ArrayList<WeatherPrediction>();
        ObjectMapper objectMapper = getObjectMapper();
        var jsonStr = Files.readString(Path.of("predictions.json"));
        return  new ArrayList(Arrays.asList(objectMapper.readValue(jsonStr, WeatherPrediction[].class ) ));
    }


    private void writeAllToFile(List<WeatherPrediction> weatherPredictions) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);


        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, weatherPredictions);

        Files.writeString(Path.of("predictions.json"), stringWriter.toString());

    }


    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }




}
