package se.systementor.webservicejavadag1.services;

import se.systementor.webservicejavadag1.models.WeatherPrediction;

import java.util.List;


public interface WeatherProvider {
    List<WeatherPrediction> getPredictionsForRestOfDay(float longitude, float latitude);
}
