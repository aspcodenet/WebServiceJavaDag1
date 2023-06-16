package models;

import java.util.Date;
import java.util.UUID;

public class WeatherPrediction {
    private UUID id;
    private Date created;
    private Date updated;
    private float longitude;
    private float latitude;
    private int predictionDatum; //20230616
    private int predictionHour; //8
    private int predictionTemperature;
    private boolean rainOrSnow;
    private ApiProvider apiProvider;

    public WeatherPrediction(UUID id) {
        this.id = id;
        this.created = new Date();
    }

    public WeatherPrediction() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
