package com.ajay.colourtemperature.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Slf4j
public class TemperatureUtility {

    public int getColourTemperature(String sunrise, String sunset, String twilightstart , String twilightend) throws ParseException {
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:m:s a");
        LocalTime sunrise_time= LocalTime.from(parseFormat.parse(sunrise));
        LocalTime sunset_time= LocalTime.from(parseFormat.parse(sunset));
        LocalTime twilightstart_time= LocalTime.from(parseFormat.parse(twilightstart));
        LocalTime twilightend_time= LocalTime.from(parseFormat.parse(twilightend));
        LocalTime currentTime =  LocalTime.from(LocalTime.now());
        log.info("sun rise is at {}",sunrise_time);
        log.info("sun set is at {}",sunset_time);
        log.info("twilightstart_time  is at {}",twilightstart_time);
        log.info("twilightend_time  is at {}",twilightend_time);
        log.info("current time   is at {}",currentTime);
        Long twilight1= ChronoUnit.MINUTES.between(twilightstart_time,sunrise_time);
        Long twilight2= ChronoUnit.MINUTES.between(sunset_time,twilightend_time);
        LocalTime peaktempTimeStart=sunrise_time.plusMinutes(twilight1);
        log.info("peaktemp  Start Time is at {}",peaktempTimeStart);
        LocalTime peaktempTimeEnd=sunset_time.minusMinutes(twilight2);
        log.info("peaktemp End Time is at {}",peaktempTimeEnd);
        if(currentTime.isAfter(peaktempTimeStart) && currentTime.isBefore(peaktempTimeEnd))
            return 6000;
        if(currentTime.isBefore(twilightstart_time) || currentTime.isAfter(twilightend_time))
            return 2700;
        return  3200;
    }
}
