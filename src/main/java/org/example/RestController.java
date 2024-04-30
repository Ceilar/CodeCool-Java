package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    TrafficHandler trafficHandler = new TrafficHandler();
@Autowired
TrafficRepository trafficRepository;

    public RestController() throws IOException {
    }

 /*   @GetMapping("/")
    public List<RecordedTraffic> showAllTraffic(){
        return trafficRepository.findAll();
    } */

    @GetMapping("/")
    ResponseEntity<List<RecordedTraffic>> showAll(){
        return new ResponseEntity<>(trafficRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/number-of-entries")
    String returnNumberOfEntries(){
        return trafficHandler.printNumberOfEntries(trafficRepository);
    }

    @GetMapping("/vehicles-after-9")
    String returnVehiclesAfter9(){
        return trafficHandler.printVehiclesAfter9(trafficRepository);
    }

    @GetMapping("/fastest-vehicle")
    String findFastestVehicle(){
        return trafficHandler.findFastestVehicle(trafficRepository);
    }

    @GetMapping("/all-speeding-vehicles")
    float findAllSpeedingVehicles(){
        return trafficHandler.findAllSpeedingVehicles(trafficRepository);
    }


    @GetMapping("/time-input")
    public String
    userInput(@RequestParam(value = "input") String input){
        return trafficHandler.requestUserInput(input,trafficRepository);
    }






}
