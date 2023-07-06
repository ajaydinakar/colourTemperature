package com.ajay.colourtemperature;


import com.ajay.colourtemperature.model.ColourTemperatureResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ColourTemperatureApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("int")
class ColourTemperatureApiIntTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setUp() throws IOException {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }

    @Test
    public void getColourTemperaturelocalWireMock() {
        configureFor("localhost", 8080);
        stubFor(get(urlMatching("/json\\?lat=.*&lng=.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("" +
                                "{\n" +
                                "      \"results\": {\n" +
                                "        \"sunrise\": \"9:38:10 AM\",\n" +
                                "        \"sunset\": \"1:04:44 AM\",\n" +
                                "        \"solar_noon\": \"5:21:27 PM\",\n" +
                                "        \"day_length\": \"15:26:34\",\n" +
                                "        \"civil_twilight_begin\": \"9:04:09 AM\",\n" +
                                "        \"civil_twilight_end\": \"1:38:45 AM\",\n" +
                                "        \"nautical_twilight_begin\": \"8:17:29 AM\",\n" +
                                "        \"nautical_twilight_end\": \"2:25:25 AM\",\n" +
                                "        \"astronomical_twilight_begin\": \"7:19:05 AM\",\n" +
                                "        \"astronomical_twilight_end\": \"3:23:49 AM\"\n" +
                                "      },\n" +
                                "      \"status\": \"OK\"\n" +
                                "    }"))
        );


        String apiurl = "http://localhost:" + port + "/colour-temperature";
        UriComponents buildUrl = UriComponentsBuilder.fromUriString(apiurl)
                .queryParam("lat", 43.66258321585993)
                .queryParam("lng", -79.39152689466948).build();
        ResponseEntity<ColourTemperatureResponse> response = testRestTemplate.getForEntity(buildUrl.toString(), ColourTemperatureResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getTemperature());
        assertThat(response.getBody().getTemperature(), anyOf(is(6000L), is(3200L), is(2700L)));
    }

    @AfterAll
    static void tearDown() throws IOException {
        wireMockServer.stop();
    }
}
