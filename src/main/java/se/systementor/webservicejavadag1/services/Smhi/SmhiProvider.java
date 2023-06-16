package se.systementor.webservicejavadag1.services.Smhi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.systementor.webservicejavadag1.models.ApiProvider;
import se.systementor.webservicejavadag1.models.WeatherPrediction;
import se.systementor.webservicejavadag1.services.Smhi.Data.Parameter;
import se.systementor.webservicejavadag1.services.Smhi.Data.Root;
import se.systementor.webservicejavadag1.services.Smhi.Data.TimeSeries;
import se.systementor.webservicejavadag1.services.WeatherProvider;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// https://json2csharp.com/code-converters/json-to-pojo


 // VI ANVÄNDER t och pcat
// https://opendata.smhi.se/apidocs/metfcst/parameters.html


public class SmhiProvider implements WeatherProvider {


    private static String url = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/%s/lat/%s/data.json";

    @Override
    public List<WeatherPrediction> getPredictionsForRestOfDay(float longitude, float latitude) {
        var list = new ArrayList<WeatherPrediction>();

        var urlToUse = String.format(url,toPoint(longitude),toPoint(latitude));
        String jsonString = null;
        try {
            jsonString = apiRequest(urlToUse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ObjectMapper om = new ObjectMapper();
        try {
            LocalDateTime today = LocalDate.now().atTime(23,59,59);
            Root root = om.readValue(jsonString, Root.class);
            for(TimeSeries series: root.timeSeries){
                WeatherPrediction weatherPrediction = new WeatherPrediction();

                LocalDateTime thisDate =  new java.sql.Date(series.validTime.getTime()).toLocalDate().atTime(0,0);


                if(thisDate.isAfter(today)) continue;
                list.add(weatherPrediction);
                weatherPrediction.setId(UUID.randomUUID());

                weatherPrediction.setPredictionDatum(Integer.parseInt( new SimpleDateFormat("yyyyMMdd").format(series.validTime) ));



                weatherPrediction.setApiProvider(ApiProvider.Smhi);
                weatherPrediction.setPredictionHour(Integer.parseInt( new SimpleDateFormat("HH").format(series.validTime) ));
                weatherPrediction.setLatitude(latitude);
                weatherPrediction.setLongitude(longitude);

                for(Parameter parameter : series.parameters)
                {
                    if(parameter.name.equals("t"))
                        weatherPrediction.setPredictionTemperature(parameter.values.get(0).intValue());
                    if(parameter.name.equals("pcat"))
                        weatherPrediction.setRainOrSnow(parameter.values.get(0).intValue() > 0);
                }

            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return list;

    }

    private String toPoint(float longitude) {
        return String.format("%.3f",longitude).replace(",",".");
    }


    String apiRequest(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
