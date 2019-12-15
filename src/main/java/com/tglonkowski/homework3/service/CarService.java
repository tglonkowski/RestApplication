package com.tglonkowski.homework3.service;

import com.tglonkowski.homework3.model.Car;
import com.tglonkowski.homework3.model.Color;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getAllCars();

    Optional<Car> getCar(long id);

    List<Car> getCar(String color);

    boolean addCar(Car car);

    boolean modifyCar(Car car);

    boolean modifyCarById(long id, String mark, String model, Color color);

    boolean removeCar(long id);
}
