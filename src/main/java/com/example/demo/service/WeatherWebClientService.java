package com.example.demo.service;
import com.example.demo.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WeatherWebClientService{

    private String apiUrl;
    private String apiKey;
    private final WebClient webClient = WebClient.create();

    public Mono<WeatherResponse> getWeatherByCity(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(apiUrl)
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }
}






