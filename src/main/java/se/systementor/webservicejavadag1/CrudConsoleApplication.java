package se.systementor.webservicejavadag1;

import models.ApiProvider;
import models.WeatherPrediction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import services.PredictionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class CrudConsoleApplication implements CommandLineRunner {

    private final PredictionService predictionService = new PredictionService();

    @Override
    public void run(String... args) throws Exception {
        var scan = new Scanner(System.in);

        while(true){
            showHeaderMenu();
            System.out.print("Action:");
            int sel = Integer.parseInt(scan.nextLine()) ;
            if(sel == 1) listPredictions(scan);
            else if(sel == 2) createNewPrediction(scan);
            else if(sel == 3) {
                updatePrediction(scan);
            } else if(sel == 9) break;
        }
    }

    private void updatePrediction(Scanner scan) {
        System.out.printf("Ange vilken dag, ex %s%n", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        int dag = Integer.parseInt(scan.nextLine()) ;

        var list = predictionService.Search(dag,0,23);
        int num = 0;
        for(var prediction : predictionService.Search(dag,0,23)){
            System.out.printf("%d Kl %d:00  Temp:%d  Nederbörd:%s   Från:%s %n", num+1,  prediction.getPredictionHour(), prediction.getPredictionTemperature(), prediction.isRainOrSnow() ? "Ja":"Nej", prediction.getApiProvider()  );
            num++;
        }
        System.out.print("Select a row number:");
        int sel = Integer.parseInt ( scan.nextLine() );;
        var t = list.get(sel-1);

        System.out.print("New temperature:");
        int temp = Integer.parseInt ( scan.nextLine() );

        System.out.print("New Rain or snow J/N:");
        boolean rainorsnow =  scan.nextLine().toLowerCase().startsWith("j") ;

        t.setPredictionTemperature(temp);
        t.setRainOrSnow(rainorsnow);

        predictionService.update(t);
    }

    private void createNewPrediction(Scanner scan) {
        System.out.println("*** CREATE PREDICTION ***");
        System.out.printf("Ange vilken dag, ex %s:", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        int dag = Integer.parseInt(scan.nextLine()) ;

        System.out.print("Hour:");
        int hour = Integer.parseInt ( scan.nextLine() );

        System.out.print("Temperature:");
        int temp = Integer.parseInt ( scan.nextLine() );

        System.out.print("Rain or snow J/N:");
        boolean rainorsnow =  scan.nextLine().toLowerCase().startsWith("j") ;

        System.out.print("Provider: (S=Smhi, O=OpenWeatherMap)");
        String input = scan.nextLine();
        ApiProvider provider;
        if(input.equals("S")) {
            provider = ApiProvider.Smhi;
        } else{
            provider = ApiProvider.OpenWeatherMap;
        }

        var weatherPrediction = new WeatherPrediction(UUID.randomUUID());
        weatherPrediction.setPredictionDatum(dag);
        weatherPrediction.setPredictionTemperature(temp);
        weatherPrediction.setPredictionHour(hour);
        weatherPrediction.setApiProvider(provider);
        weatherPrediction.setRainOrSnow(rainorsnow);

        if(predictionService.createNew(weatherPrediction)){
            System.out.println("Created ok");
        }else{
            System.out.println("Error - duplicate?");
        }


    }

    private void listPredictions(Scanner scan) {
        System.out.printf("Ange vilken dag, ex %s%n", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        int dag = Integer.parseInt(scan.nextLine()) ;
        for(var prediction : predictionService.Search(dag,0,23)){
            System.out.printf("Kl %d:00  Temp:%d  Nederbörd:%s   Från:%s %n", prediction.getPredictionHour(), prediction.getPredictionTemperature(), prediction.isRainOrSnow() ? "Ja":"Nej", prediction.getApiProvider()  );
        }

    }

    private void showHeaderMenu() {
        System.out.println("1. See registrations");
        System.out.println("2. Create registration");
        System.out.println("3. Update registration");
        System.out.println("9. Exit");
    }
}
