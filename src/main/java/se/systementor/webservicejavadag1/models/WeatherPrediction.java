package se.systementor.webservicejavadag1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class WeatherPrediction {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private float longitude;
    private float latitude;
    private int predictionDatum; //20230616
    private int predictionHour; //8
    private int predictionTemperature;
    private boolean rainOrSnow;
    private ApiProvider apiProvider;

    public WeatherPrediction(UUID id) {
        this.id = id;
        this.created = LocalDateTime.now();
    }

    public WeatherPrediction() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getPredictionDatum() {
        return predictionDatum;
    }

    public void setPredictionDatum(int predictionDatum) {
        this.predictionDatum = predictionDatum;
    }

    public int getPredictionHour() {
        return predictionHour;
    }

    public void setPredictionHour(int predictionHour) {
        this.predictionHour = predictionHour;
    }

    public int getPredictionTemperature() {
        return predictionTemperature;
    }

    public void setPredictionTemperature(int predictionTemperature) {
        this.predictionTemperature = predictionTemperature;
    }

    public boolean isRainOrSnow() {
        return rainOrSnow;
    }

    public void setRainOrSnow(boolean rainOrSnow) {
        this.rainOrSnow = rainOrSnow;
    }

    public ApiProvider getApiProvider() {
        return apiProvider;
    }

    public void setApiProvider(ApiProvider apiProvider) {
        this.apiProvider = apiProvider;
    }

    public int SORT_HOUR(WeatherPrediction weatherPrediction) {
        return weatherPrediction.predictionHour;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
