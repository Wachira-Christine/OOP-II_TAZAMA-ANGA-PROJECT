package com.example.WeatherApiClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherApiClient {
    private final RestTemplate restTemplate;
    private final WeatherApiUrlBuilder urlBuilder;

    public WeatherApiClient(RestTemplate restTemplate, WeatherApiUrlBuilder urlBuilder) {
        this.restTemplate = restTemplate;
        this.urlBuilder = urlBuilder;
    }

    public String fetchCurrentWeather(String city) {
        String url = urlBuilder.buildCurrentWeatherUrl(city);
        return makeApiCall(url, "Failed to fetch current weather data");
    }

    public String fetch7DayForecast(String city) {
        String url = urlBuilder.build7DayForecastUrl(city);
        return makeApiCall(url, "Failed to fetch 7-day forecast");
    }

    private String makeApiCall(String url, String errorMessage) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(errorMessage, e);
        }
    }
}

@Component
class WeatherApiUrlBuilder {
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    public String buildCurrentWeatherUrl(String city) {
        return String.format("%s/weather?q=%s&units=metric&appid=%s",
                baseUrl, city, apiKey);
    }

    public String build7DayForecastUrl(String city) {
        return String.format("%s/forecast/daily?q=%s&cnt=7&units=metric&appid=%s",
                baseUrl, city, apiKey);
    }
}
