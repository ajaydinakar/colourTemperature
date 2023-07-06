package com.ajay.colourtemperature.controller;


import com.ajay.colourtemperature.model.ColourTemperatureResponse;
import com.ajay.colourtemperature.service.ColourTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class ColourTemperatureController {

    @Autowired
    ColourTemperatureService service;

    @GetMapping("/colour-temperature")
    ResponseEntity<ColourTemperatureResponse> getTemperature(@RequestParam("lat") float latitude, @RequestParam("lng") float longitude) throws ParseException {
        ColourTemperatureResponse response = new ColourTemperatureResponse();
        response.setTemperature(service.getTemperature(String.valueOf(latitude), String.valueOf(longitude)));
        ResponseEntity<ColourTemperatureResponse> entity = new ResponseEntity<>(response, HttpStatus.OK);
        return entity;
    }
}
