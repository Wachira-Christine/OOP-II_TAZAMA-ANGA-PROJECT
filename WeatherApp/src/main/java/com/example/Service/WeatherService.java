package com.example.Service;

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
    private final WeatherApiClient apiClient;
    private final WeatherResponseParser responseParser;

    public WeatherService(RestTemplateBuilder builder) {
        this.apiClient = new WeatherApiClient(builder.build());
        this.responseParser = new WeatherResponseParser();
    }

    public WeatherData getCurrentWeather(String city) {
        String response = apiClient.fetchCurrentWeather(city);
        return responseParser.parseCurrentWeather(response, city);
    }

    public List<WeatherData> get7DayForecast(String city) {
        String response = apiClient.fetch7DayForecast(city);
        return responseParser.parse7DayForecast(response, city);
    }
}

class WeatherApiClient {
    private final String apiKey = "2068715ee6c70f01debfa82701461083";
    private final String currentWeatherUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast";
    private final RestTemplate restTemplate;

    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchCurrentWeather(String city) {
        String url = currentWeatherUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("API Error: " + e.getMessage());
        }
    }

    public String fetch7DayForecast(String city) {
        String url = forecastUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching forecast: " + e.getMessage());
        }
    }
}

class WeatherResponseParser {
    public WeatherData parseCurrentWeather(String responseBody, String city) {
        JSONObject json = new JSONObject(responseBody);
        JSONObject main = json.getJSONObject("main");
        JSONObject wind = json.getJSONObject("wind");
        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);

        WeatherData data = new WeatherData();
        data.setCityName(city);
        data.setTemperature(main.getDouble("temp"));
        data.setHumidity(main.getInt("humidity"));
        data.setWindSpeed(wind.getDouble("speed"));
        data.setDescription(weather.getString("description"));
        data.setDate(LocalDateTime.now());

        return data;
    }

    public List<WeatherData> parse7DayForecast(String responseBody, String city) {
        JSONObject json = new JSONObject(responseBody);
        JSONArray list = json.getJSONArray("list");
        List<WeatherData> dailyForecast = new ArrayList<>();

        for (int i = 4; i < list.length() && dailyForecast.size() < 5; i += 8) {
            JSONObject entry = list.getJSONObject(i);
            JSONObject main = entry.getJSONObject("main");
            JSONObject wind = entry.getJSONObject("wind");
            JSONObject weather = entry.getJSONArray("weather").getJSONObject(0);

            WeatherData data = new WeatherData();
            data.setCityName(city);
            data.setTemperature(main.getDouble("temp"));
            data.setHumidity(main.getInt("humidity"));
            data.setWindSpeed(wind.getDouble("speed"));
            data.setDescription(weather.getString("description"));

            long timestamp = entry.getLong("dt");
            data.setDate(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC));

            dailyForecast.add(data);
        }

        return dailyForecast;
    }
}