package com.tglonkowski.homework3.service;

import com.tglonkowski.homework3.model.Car;
import com.tglonkowski.homework3.model.Color;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private List<Car> cars;

    public CarServiceImpl() {
        this.cars = new ArrayList<>();
    }

    @Override
    public List<Car> getAllCars() {
        return cars;
    }

    @Override
    public Optional<Car> getCar(long id) {
        return cars.stream().filter(car -> car.getId() == id).findFirst();
    }

    @Override
    public List<Car> getCar(String color) {
        return cars.stream().filter(car -> color.equalsIgnoreCase(car.getColor().name())).collect(Collectors.toList());
    }

    @Override
    public boolean addCar(Car car) {
        Optional<Car> optionalCar = carExists(car);
        if (optionalCar.isPresent()) {
            return false;
        } else
            return cars.add(car);
    }

    @Override
    public boolean modifyCar(Car car) {
        Optional<Car> optionalCar = carExists(car);
        if (optionalCar.isPresent()) {
            Car carToModify = optionalCar.get();
            carToModify.setId(car.getId());
            carToModify.setMark(car.getMark());
            carToModify.setModel(car.getModel());
            carToModify.setColor(car.getColor());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCar(long id) {
        Optional<Car> carById = getCar(id);
        return carById.map(car -> cars.remove(car)).orElse(false);
    }

    @Override
    public boolean modifyCarById(long id, String mark, String model, Color color) {
        Optional<Car> optionalCar = getCar(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            if (mark != null) car.setMark(mark);
            if (model != null) car.setModel(model);
            if (color != null) car.setColor(color);
            return true;
        }
        return false;
    }

    private Optional<Car> carExists(Car car) {
        return cars.stream().filter(car1 -> car1.getId().equals(car.getId())).findFirst();
    }
}
