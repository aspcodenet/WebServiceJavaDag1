package se.systementor.webservicejavadag1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.systementor.webservicejavadag1.models.AverageDTO;
import se.systementor.webservicejavadag1.models.WeatherPrediction;
import se.systementor.webservicejavadag1.repositories.WeatherPredictionRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
            newPrediction.setId(UUID.randomUUID());
        newPrediction.setCreated(LocalDateTime.now());
        newPrediction.setUpdated(LocalDateTime.now());
        weatherPredictionRepository.save(newPrediction);
        return true;
    }

    public void update(WeatherPrediction t) {
        t.setUpdated(LocalDateTime.now());
        weatherPredictionRepository.save(t);
    }


    public List<AverageDTO> calculateAverage(LocalDate dag) {
        var resultList = new ArrayList<AverageDTO>();
        //returnera EN AverageDTO-objekt per timme
        //1 ta fram alla poster från databasen med datum == dag

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatDateTime = dag.format(formatter);
        int datumAsInt =Integer.parseInt(formatDateTime);
        // 20230911


        var allPredictionsForDay = weatherPredictionRepository.findAllByPredictionDatum(datumAsInt);

        //2 for loop timme = 0 to 24
        for(int timme = 0; timme <= 23; timme++){
            var averageDto = new AverageDTO();
            averageDto.setHour(timme);
            averageDto.setDate(dag);
            float antal = 0;
            float summa = 0;
//            for(int i = 0; i < allPredictionsForDay.size(); i++){
//                var prediction = allPredictionsForDay.get(i);
//           }

            for(WeatherPrediction weatherPrediction : allPredictionsForDay){
                if(weatherPrediction.getPredictionHour() == timme){
                    antal++;
                    summa += weatherPrediction.getPredictionTemperature();
                }
            }
            if(antal > 0) {
                averageDto.setAverage(summa / antal);
                resultList.add(averageDto);
            }
        }
        //      loopa igenom allPredictionsForDay och räkna average för timme 0
        //      lagra i list
        //

        return resultList;
    }
}
