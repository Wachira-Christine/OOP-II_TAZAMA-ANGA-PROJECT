package com.example.Service;

import com.example.model.CurrentWeatherData;
import com.example.model.ForecastWeatherData;
import com.example.model.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;
    private final String apiKey = "2068715ee6c70f01debfa82701461083";
    private final String currentWeatherUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast";

    public WeatherService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public CurrentWeatherData getCurrentWeather(String city) {
        String url = currentWeatherUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return parseCurrentWeatherResponse(response.getBody(), city);
        } catch (Exception e) {
            throw new RuntimeException("API Error: " + e.getMessage());
        }
    }

    public List<ForecastWeatherData> get7DayForecast(String city) {
        String url = forecastUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return parseForecastResponse(response.getBody(), city);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching forecast: " + e.getMessage());
        }
    }

    private CurrentWeatherData parseCurrentWeatherResponse(String responseBody, String city) {
        JSONObject json = new JSONObject(responseBody);
        JSONObject main = json.getJSONObject("main");
        JSONObject wind = json.getJSONObject("wind");
        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);

        return new CurrentWeatherData(
                main.getDouble("temp"),
                main.getInt("humidity"),
                wind.getDouble("speed"),
                weather.getString("description"),
                LocalDateTime.now(),
                city,
                main.getDouble("feels_like") // Additional feels-like temp
        );
    }

    private List<ForecastWeatherData> parseForecastResponse(String responseBody, String city) {
        JSONObject json = new JSONObject(responseBody);
        JSONArray list = json.getJSONArray("list");
        List<ForecastWeatherData> dailyForecast = new ArrayList<>();

        for (int i = 4; i < list.length() && dailyForecast.size() < 5; i += 8) {
            JSONObject entry = list.getJSONObject(i);
            JSONObject main = entry.getJSONObject("main");
            JSONObject wind = entry.getJSONObject("wind");
            JSONObject weather = entry.getJSONArray("weather").getJSONObject(0);

            dailyForecast.add(new ForecastWeatherData(
                    main.getDouble("temp"),
                    main.getInt("humidity"),
                    wind.getDouble("speed"),
                    weather.getString("description"),
                    LocalDateTime.ofEpochSecond(entry.getLong("dt"), 0, ZoneOffset.UTC),
                    city
            ));
        }
        return dailyForecast;
    }
}