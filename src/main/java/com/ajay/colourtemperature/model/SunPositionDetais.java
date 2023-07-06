package com.ajay.colourtemperature.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SunPositionDetais {
    String sunrise;
    String sunset;
    String solar_noon;
    String day_length;
    String civil_twilight_begin;
    String civil_twilight_end;
    String nautical_twilight_begin;
    String nautical_twilight_end;
    String astronomical_twilight_begin;
    String astronomical_twilight_end;

}
