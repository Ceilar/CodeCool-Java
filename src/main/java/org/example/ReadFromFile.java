package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;


public class ReadFromFile {
    RecordedTraffic recordedTraffic;
    ArrayList<RecordedTraffic> listOfTraffic = new ArrayList<>();
    String file = "measurements.txt";
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    String lineReader;
    String[] information;


    public ReadFromFile() throws FileNotFoundException {

    }

    public ArrayList<RecordedTraffic> readFromFile() throws IOException {
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
        return listOfTraffic;
    }
    public static LocalTime formatTime(String hours, String minutes, String seconds, String miliseconds) {
        return LocalTime.of(Integer.parseInt(hours),
                Integer.parseInt(minutes),
                Integer.parseInt(seconds),
                Integer.parseInt(miliseconds) * 1000000
        );
    }
}
