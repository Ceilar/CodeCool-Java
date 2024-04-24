package org.example;


import java.time.LocalTime;

public class RecordedTraffic {
    private String licensePlate;
    private LocalTime timeOfEntry;
    private LocalTime timeOfExit;
    public RecordedTraffic(String licensePlate, LocalTime timeOfEntry, LocalTime timeOfExit) {
        this.licensePlate = licensePlate;
        this.timeOfEntry = timeOfEntry;
        this.timeOfExit = timeOfExit;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalTime getTimeOfEntry() {
        return timeOfEntry;
    }

    public LocalTime getTimeOfExit() {
        return timeOfExit;
    }



}
