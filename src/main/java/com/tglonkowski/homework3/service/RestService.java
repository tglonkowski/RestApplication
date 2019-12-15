package com.tglonkowski.homework3.service;

import com.tglonkowski.homework3.model.Car;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RestService {

    private RestTemplate restTemplate;
    private final String URL = "http://localhost:8080/cars";

    public RestService() {
        restTemplate = new RestTemplate();
    }

    public List<Car> getAllCars() {
        ResponseEntity<List<Car>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Car>>() {
                });
        return responseEntity.getBody();
    }

    public Car getCar(long id) {
        String url = URL + "/" + id;
        ResponseEntity<Car> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Car>() {
                });
        return responseEntity.getBody();
    }

    public List<Car> getCar(String color) {
        String url = "http://localhost:8080/cars/color/" + color;
        ResponseEntity<List<Car>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Car>>() {
                });
        return responseEntity.getBody();
    }

    public void addCar(Car car) throws URISyntaxException {
        restTemplate.postForObject(new URI(URL),car,Car.class);
    }

    public void deleteCar(long id) throws URISyntaxException {
        String url = URL + "/" + id;
        restTemplate.delete(new URI(url));
    }

    public void updateCar(Car car) {
        restTemplate.put(URL,car);
    }
}
