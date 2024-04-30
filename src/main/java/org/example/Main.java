package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.io.IOException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {
        /*TrafficHandler trafficHandler = new TrafficHandler();
        //Exercise 2
        trafficHandler.printNumberOfEntries();
        System.out.println("------------------------------------");
        //Exercise 3
        trafficHandler.printVehiclesAfter9();
        System.out.println("------------------------------------");
        //Exercise 4
        System.out.println("Exercise 4.\nEnter an hour and minute value:");
        trafficHandler.requestUserInput();
        //Exercise 5
        System.out.println("------------------------------------");
        System.out.println("Exercise 5.");
        trafficHandler.findFastestVehicle();
        //Exercise 6
        System.out.println("------------------------------------");
        System.out.println("Exercise 6.");
        trafficHandler.findAllSpeedingVehicles();

*/

        SpringApplication.run(Main.class,args);

    }


}