
# ColourTemperature Microservice

ColourTemperature microservice is a application , which determines colour temperature of the screen according to current position of the sun. This will Calculate temperature in Kelvins according to below figure

we have three regions of colour tempereatures peak or the day and temperatures before twilight and after twilight which are constant and the gradually increasing and decreasing of the time between these two 

![Alt text](https://sunrise-sunset.org/graph.svg)


this caters  a GET request with the following parameters:
- lat (float): Latitude in decimal degrees.
- lng (float): Longitude in decimal degrees.

## Sample request
localhost:8089/colour-temperature?lat=43.66258321585993&lng=-79.39152689466948

## Response
{
“temperature”:3400
}

## Implementation
- Used https://start.spring.io/ to create Gradle Project for Java or Kotlin;
- Used https://sunrise-sunset.org/api for determining sunrise/sunset/twilight;
- Created Dockerfile to run microservice as a Docker container;
- Added JUnit **integration** tests to verify correctness of service execution (test should be executed using Gradle integrationTest task, see https://docs.gradle.org/current/userguide/java_testing.html#sec:configuring_java_integration_tests);
- Created Service Virtualisation to mock sunrise-sunset.org API using [WireMock](http://wiremock.org/);
- WireMock  ran as a Docker container as well - refered https://hub.docker.com/r/rodolpheche/wiremock/;
- Provide two Spring Boot profiles - dev and qa. Dev profile  uses WireMock and qa profile  uses real sunrise-sunset.org service;
- Used docker-compose to define and run ColourTemperature service and WireMock containers together;


## Execution Instructions


> ## Instructions
>### Building the app
> to build the application , excecute "gradle clean build"
>### Running Integration Tests for  the app
> excecute command  "gradle integrationTest" or direclty run the integrationTest task on intellij
> 
> seperately runnnig the test case from run configurations
> ### Running app 
> > can execute "gradle bootrun" to run the app in local 
> 
>  the app can be deployed on docker container along with a 
> second container for wiremock by executing command 
>" docker-compose up --build" which will build the images and deploy them on respective containers 
> as configured in docker compose file .
> 
> ColourTemperatureMicroservice configured to run on 8079 on app-server container 
> 
> Wiremock is configured to run on 8383 from  sunrise-sunset-api container,from local host we can call on 8383 port
> >### Environments
> the app have "qa" and "dev"  environments,
>  profiles can be changed by updating the active profile variable in application.yml

> on qa profile app directly  consumes 3rd pary api and 
> 
> on dev profile app consumes the api mocked by wiremock from docker container so make sure container is running
> 
>  >### Testing
> 
> can test the app by pinging the url localhost:8089/colour-temperature?lat=43.66258321585993&lng=-79.39152689466948


