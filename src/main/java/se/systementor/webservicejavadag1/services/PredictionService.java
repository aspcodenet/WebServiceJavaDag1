package se.systementor.webservicejavadag1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.systementor.webservicejavadag1.models.WeatherPrediction;
import se.systementor.webservicejavadag1.repositories.WeatherPredictionRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PredictionService {
    @Autowired
    WeatherPredictionRepository weatherPredictionRepository;


    public List<WeatherPrediction> Search(int dag, int fromHour, int toHour){
        return weatherPredictionRepository.findAll()
                .stream()
                .filter(prediction ->  prediction.getPredictionDatum() == dag && prediction.getPredictionHour() >= fromHour && prediction.getPredictionHour() <= toHour )
                .sorted( WeatherPrediction::SORT_HOUR )
                .toList();

    }

    public boolean createNew(WeatherPrediction newPrediction) {
        newPrediction.setId(newPrediction.getId());
        newPrediction.setCreated(LocalDateTime.now());
        newPrediction.setUpdated(LocalDateTime.now());
        weatherPredictionRepository.save(newPrediction);
        return true;
    }

    public void update(WeatherPrediction t) {
        t.setUpdated(LocalDateTime.now());
        weatherPredictionRepository.save(t);
    }





}
