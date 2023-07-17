package com.ajay.colourtemperature.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Slf4j
@Component
public class TemperatureUtility {

    public int getColourTemperature(String sunrise, String sunset, String twilightstart , String twilightend) throws ParseException {
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:m:s a");
        LocalTime sunrise_time= LocalTime.from(parseFormat.parse(sunrise));
        LocalTime sunset_time= LocalTime.from(parseFormat.parse(sunset));
        LocalTime twilightstart_time= LocalTime.from(parseFormat.parse(twilightstart));
        LocalTime twilightend_time= LocalTime.from(parseFormat.parse(twilightend));
        LocalTime currentTime =  LocalTime.from(LocalTime.now());
        Long twilight1= ChronoUnit.MINUTES.between(twilightstart_time,sunrise_time);
        log.info("twilight1 Time is  {}",twilight1);
        Long twilight2= ChronoUnit.MINUTES.between(sunset_time,twilightend_time);
        log.info("twilight2 Time is  {}",twilight2);
        LocalTime peaktempTimeStart=sunrise_time.plusMinutes(twilight1);

        log.info("peaktemp  Start Time is at {}",peaktempTimeStart);
        LocalTime peaktempTimeEnd=sunset_time.minusMinutes(twilight2);
        log.info("peaktemp End Time is at {}",peaktempTimeEnd);
        if(currentTime.isAfter(peaktempTimeStart) && currentTime.isBefore(peaktempTimeEnd))
            return 6000;
        if(currentTime.isBefore(twilightstart_time) || currentTime.isAfter(twilightend_time))
            return 2700;
        long currentMinutes=1;
        if(currentTime.isBefore(peaktempTimeStart) && currentTime.isAfter(sunrise_time))
        {
            currentMinutes= ChronoUnit.MINUTES.between(twilightstart_time,currentTime);
            log.info("currentMinutes Time is  {}",currentMinutes);
        }
        if(currentTime.isAfter(sunset_time)&& currentTime.isBefore(twilightend_time))
        {
            currentMinutes= ChronoUnit.MINUTES.between(currentTime,twilightend_time);
            log.info("currentMinutes Time is  {}",currentMinutes);
        }
        int temperatureRatio= (int) (3300/twilight1);
        int colourTemperature = (int) (2700+(temperatureRatio*currentMinutes));

        return  colourTemperature;
    }


}
