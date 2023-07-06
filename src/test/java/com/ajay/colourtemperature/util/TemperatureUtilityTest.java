package com.ajay.colourtemperature.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class TemperatureUtilityTest {

    @Test
    void getColourTemperature() throws ParseException {
        TemperatureUtility utility =new TemperatureUtility();
       int temp= utility.getColourTemperature("05:38:10 AM","08:04:44 PM","04:19:05 AM","09:23:49 PM" );
       //the value will be based on current time so will be one of these values
        assertThat(temp,anyOf(is(6000),is(3200),is(2700)));

    }
}