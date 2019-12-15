package com.tglonkowski.homework3.api;

import com.tglonkowski.homework3.model.Car;
import com.tglonkowski.homework3.model.Color;
import com.tglonkowski.homework3.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cars",
        produces = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE})
public class CarApi {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> optionalCar = carService.getCar(id);
        return optionalCar.map(car -> new ResponseEntity<>(car, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarByColor(@PathVariable String color) {
        List<Car> car = carService.getCar(color);
        return car.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        boolean addedCar = carService.addCar(car);
        return addedCar ? new ResponseEntity<>(car, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeCar(@PathVariable long id) {
        boolean removedCar = carService.removeCar(id);
        return removedCar ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Car> modifyCar(@RequestBody Car car) {
        boolean modifyCar = carService.modifyCar(car);
        return modifyCar ? new ResponseEntity<>(car, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PatchMapping("{id}")
    public ResponseEntity modifyCarById(@PathVariable long id,
                                        @RequestParam(required = false) String mark,
                                        @RequestParam(required = false) String model,
                                        @RequestParam(required = false) Color color) {
        boolean modifiedCar = carService.modifyCarById(id, mark, model, color);
        return modifiedCar ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
