package com.tglonkowski.homework3.common;

import com.tglonkowski.homework3.model.Car;
import com.tglonkowski.homework3.model.Color;
import com.tglonkowski.homework3.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private CarService carService;

    @Autowired
    public DataLoader(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading Data...");
        log.info("Loading Data...");
        addCars();
        log.info("Finished");
    }

    private void addCars() {
        buildCar(1L, "HONDA", "CIVIC", Color.BLUE);

        buildCar(2L, "MERCEDES", "CLA", Color.GREY);

        buildCar(3L, "VOLVO", "S40", Color.WHITE);

        buildCar(4L, "BMW", "X6", Color.BLUE);

        buildCar(5L, "TOYOTA", "COROLLA", Color.SILVER);
    }

    private void buildCar(long id, String mark, String model, Color color) {
        carService
                .getAllCars()
                .add(Car.builder()
                        .id(id)
                        .mark(mark)
                        .model(model)
                        .color(color)
                        .build());
    }
}
