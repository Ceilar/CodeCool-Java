package org.example;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class TrafficHandler {
    @Autowired
    TrafficRepository trafficRepository;
    ReadFromFile reader;

    {
        try {
            reader = new ReadFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    ArrayList<RecordedTraffic> listOfTraffic = reader.readFromFile();

    public TrafficHandler() throws IOException {
    }

    @PostConstruct
    public void populateDatabase() {
        for (int i = 0; i < listOfTraffic.size(); i++) {
            trafficRepository.save(new RecordedTraffic(listOfTraffic.get(i).getLicensePlate(),
                    Time.valueOf(listOfTraffic.get(i).getTimeOfEntry()).toLocalTime(),
                    Time.valueOf(listOfTraffic.get(i).getTimeOfExit()).toLocalTime()));
        }
    }


    public String printNumberOfEntries(TrafficRepository trafficRepository) {
        return ("Exercise 2.\nThe data of " + trafficRepository.count() + " vehicles were recorded in the measurement");
    }

    public String printVehiclesAfter9(TrafficRepository trafficRepository) {
        List<RecordedTraffic> list = trafficRepository.findAll();
        int countOfVehiclesBefore9 = 0;
        for (RecordedTraffic recordedTraffic : list) {
            if (recordedTraffic.getTimeOfExit().isBefore(LocalTime.of(9, 0))) {
                countOfVehiclesBefore9++;
            }
        }
        return ("Exercise 3.\nBefore 9 o`clock " + countOfVehiclesBefore9 + " vehicles passed the exit point recorder.");
    }

    public String requestUserInput(String input, TrafficRepository trafficRepository) {
        String[] inputSplitter = input.split(" ");
        List<RecordedTraffic> list = trafficRepository.findAll();
        try {
            int[] userInputtedHours = {Integer.parseInt(inputSplitter[0]), Integer.parseInt(inputSplitter[1])};
            int counter = 0;
            if ((userInputtedHours[0] < 24 && userInputtedHours[0] > 0) && (userInputtedHours[1] < 60 && userInputtedHours[1] >= 0)) {
                LocalTime timeToCompare = LocalTime.of(userInputtedHours[0], userInputtedHours[1]);

                for (int i = 0; i < trafficRepository.count(); i++) {
                    if (list.get(i).getTimeOfEntry().isAfter(timeToCompare) && list.get(i).getTimeOfEntry().isBefore(timeToCompare.plusMinutes(1))) {
                        counter++;
                    }
                }
                counter = 0;
                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i).getTimeOfExit().isAfter(timeToCompare) &&
                            list.get(i).getTimeOfEntry().isBefore(timeToCompare.plusMinutes(1))) {
                        counter++;
                    }
                }
                return ("The number of vehicle/s that passed the entry point recorder: " + counter + "\nThe traffic intencity " + counter / 10.0);

            } else {
                return ("Enter a valid 'hh:mm' format time please1");
            }


        } catch (IndexOutOfBoundsException | NumberFormatException e1) {
            return "Enter a valid 'hh:mm' format time please2";
        }
    }

    public String findFastestVehicle(TrafficRepository trafficRepository) {
        long minTime = 932943290;
        long time;
        int passedCars = 0;
        RecordedTraffic car = null;
        List<RecordedTraffic> list = trafficRepository.findAll();
        for (int i = 0; i < trafficRepository.count(); i++) {
            time = SECONDS.between(list.get(i).getTimeOfEntry(), list.get(i).getTimeOfExit());
            if (time < minTime || minTime == 0) {
                minTime = time;
                car = list.get(i);
            }
        }
        for (int i = 0; i < trafficRepository.count(); i++) {
            if (car != null
                    && car.getTimeOfEntry().isAfter(list.get(i).getTimeOfEntry()) && car.getTimeOfExit().isBefore(list.get(i).getTimeOfExit())) {
                passedCars++;
            }
        }
        return "The data of vehicle with the highest speed is:\n" + car.getLicensePlate() + "\nAverage speed:" + 10 / (minTime / (float) 3600) + " km/h" + "\nPassed cars:" + passedCars;
    }

    public float findAllSpeedingVehicles(TrafficRepository trafficRepository) {
        int speedingCars = 0;
        List<RecordedTraffic> list = trafficRepository.findAll();
        for (int i = 0; i < trafficRepository.count(); i++) {
            if (10 / (SECONDS.between(list.get(i).getTimeOfEntry(), list.get(i).getTimeOfExit()) / (float) 3600) > 90) {
                speedingCars++;
            }
        }
        return speedingCars * 100 / (float) list.size();
    }
}

