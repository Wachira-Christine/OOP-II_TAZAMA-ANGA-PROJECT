package com.example.demo.service;
import com.example.demo.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
@Service
public class WeatherService {
    
    private final String API_KEY="c0efffa54d6455f99111098dea8644b4";
    private final String API_URL="https://api.openweathermap.org/data/2.5/weather";
    public WeatherResponse getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .build().toUriString();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        return response != null ? response : new WeatherResponse(); // Ensure an object is always returned
    }
}
