package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForecastWeatherData extends WeatherData {
    public ForecastWeatherData(double temperature, int humidity, double windSpeed,
                               String description, LocalDateTime date, String cityName) {
        super(temperature, humidity, windSpeed, description, date, cityName);
    }

    @Override
    public String getDisplayTemperature() {
        return String.format("%.1f°C at %s",
                temperature,
                date.format(DateTimeFormatter.ofPattern("MMM dd hh:mm a")));
    }

    @Override
    public boolean isSevereWeather() {
        String lowerDesc = description.toLowerCase();
        return lowerDesc.matches(".*(storm|tornado|hurricane|blizzard).*");
    }

    @Override
    public String getWeatherType() {
        return "FORECAST";
    }
}