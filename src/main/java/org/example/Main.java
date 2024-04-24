package org.example;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.SECONDS;


public class Main {
    public static void main(String[] args) throws IOException {
        RecordedTraffic recordedTraffic;
        ArrayList<RecordedTraffic> listOfTraffic = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("measurements.txt"));
        String lineReader;
        String[] information;

        while ((lineReader = bufferedReader.readLine()) != null) {
            information = lineReader.split(" ");
            recordedTraffic = new RecordedTraffic(information[0], formatTime(
                    information[1],
                    information[2],
                    information[3],
                    information[4]),
                    formatTime(
                            information[5],
                            information[6],
                            information[7],
                            information[8])
            );
            listOfTraffic.add(recordedTraffic); //1
        }
        bufferedReader.close();

        printNumberOfEntries(listOfTraffic); //2

        printVehiclesAfter9(listOfTraffic); //3

        System.out.println("Exercise 4.\nEnter an hour and minute value:");
        requestUserInput(listOfTraffic);//4

        findFastestVehicle(listOfTraffic);//5

        findAllSpeedingVehicles(listOfTraffic);//6


    }

    private static void findAllSpeedingVehicles(ArrayList<RecordedTraffic> list) {
        int speedingCars = 0;
        for (int i = 0; i <= list.size() - 1; i++) {
            if (10 / (SECONDS.between(list.get(i).getTimeOfEntry(), list.get(i).getTimeOfExit()) / (float) 3600) > 90) {
                speedingCars++;
            }
        }
        System.out.println(speedingCars * 100 / (float) list.size());
    }

    private static void findFastestVehicle(ArrayList<RecordedTraffic> list) {
        long minTime = 932943290;
        long time;
        int passedCars = 0;
        RecordedTraffic car = null;
        for (int i = 0; i <= list.size() - 1; i++) {
            time = SECONDS.between(list.get(i).getTimeOfEntry(), list.get(i).getTimeOfExit());
            if (time < minTime || minTime == 0) {
                minTime = time;
                car = list.get(i);
            }
        }
        for (int i = 0; i <= list.size() - 1; i++) {
            if (car != null
                    && car.getTimeOfEntry().isAfter(list.get(i).getTimeOfEntry()) && car.getTimeOfExit().isBefore(list.get(i).getTimeOfExit())) {
                passedCars++;
            }
        }

        System.out.println("The data of vehicle with the highest speed is: " + car.getLicensePlate());
        System.out.println("Average speed:" + 10 / (minTime / (float) 3600) + " km/h");
        System.out.println("Passed cars:" + passedCars);
    }

    private static void requestUserInput(ArrayList<RecordedTraffic> list) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] inputSplitter = input.split(" ");
        try {
            int[] arr = {Integer.parseInt(inputSplitter[0]), Integer.parseInt(inputSplitter[1])};
            int counter = 0;
            if ((arr[0] < 24 && arr[0] > 0) && (arr[1] < 60 && arr[1] >= 0)) {
                LocalTime timeToCompare = LocalTime.of(arr[0], arr[1]);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTimeOfEntry().isAfter(timeToCompare) && list.get(i).getTimeOfEntry().isBefore(timeToCompare.plusMinutes(1))) {
                        counter++;
                    }
                }
                System.out.println("The number of vehicle/s that passed the entry point recorder: " + counter);
                counter = 0;
                for (int i = 0; i <= list.size() - 1; i++) {
                    if (list.get(i).getTimeOfExit().isAfter(timeToCompare) &&
                            list.get(i).getTimeOfEntry().isBefore(timeToCompare.plusMinutes(1))) {
                        counter++;
                    }
                }
                System.out.println("The traffic intencity " + counter / 10.0);

            } else {
                System.out.println("Enter a valid 'hh:mm' format time please1");
                requestUserInput(list);
            }


        } catch (IndexOutOfBoundsException|NumberFormatException e1) {
            System.out.println("Enter a valid 'hh:mm' format time please2");
            requestUserInput(list);
        }
    }

    private static void printVehiclesAfter9(ArrayList<RecordedTraffic> listOfTraffic) {
        int countBefore9 = 0;
        for (RecordedTraffic recordedTraffic : listOfTraffic) {
            if (recordedTraffic.getTimeOfExit().isBefore(LocalTime.of(9, 0))) {
                countBefore9++;
            }
        }
        System.out.println("\nExercise 3.\nBefore 9 o`clock " + countBefore9 + " vehicles passed the exit point recorder.");
    }

    public static void printNumberOfEntries(ArrayList<RecordedTraffic> list) {
        System.out.println("Exercise 2.\nThe data of " + list.size() + " vehicles were recorded in the measurement");
    }


    public static LocalTime formatTime(String hours, String minutes, String seconds, String miliseconds) {
        return LocalTime.of(Integer.parseInt(hours),
                Integer.parseInt(minutes),
                Integer.parseInt(seconds),
                Integer.parseInt(miliseconds) * 1000000
        );
    }
}