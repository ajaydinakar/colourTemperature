package com.ajay.colourtemperature.service;

import com.ajay.colourtemperature.model.SunApiResponse;
import com.ajay.colourtemperature.model.SunPositionDetais;
import com.ajay.colourtemperature.util.TemperatureUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;


@Service
@Slf4j
public class ColourTemperatureService {

    private RestTemplate restTemplate;
    @Value("${baseurl}")
    String baseUrl;

    public ColourTemperatureService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public long getTemperature(String latitdue, String longitude) throws ParseException {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("lat", latitdue)
                .queryParam("lng", longitude).build();
        log.info("url called is  {}",url);
        SunApiResponse response = restTemplate.getForObject(url.toString(), SunApiResponse.class);
        SunPositionDetais detais = response.getResults();
        TemperatureUtility utility = new TemperatureUtility();
        long temperature = utility.getColourTemperature(detais.getSunrise(), detais.getSunset(), detais.getAstronomical_twilight_begin(), detais.getAstronomical_twilight_end());
        return temperature;
    }


}
