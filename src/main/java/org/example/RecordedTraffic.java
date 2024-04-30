package org.example;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalTime;
@Entity
public class RecordedTraffic {
    @Id
    @Column
    private String licensePlate;
    @Column
    private LocalTime timeOfEntry;
    @Column
    private LocalTime timeOfExit;
    public RecordedTraffic(String licensePlate, LocalTime timeOfEntry, LocalTime timeOfExit) {
        this.licensePlate = licensePlate;
        this.timeOfEntry = timeOfEntry;
        this.timeOfExit = timeOfExit;
    }

    public RecordedTraffic() {

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
