package com.example.model;

import java.time.LocalDateTime;

public class CurrentWeatherData extends WeatherData {
    private final double feelsLike;

    public CurrentWeatherData(double temperature, int humidity, double windSpeed,
                              String description, LocalDateTime date, String cityName,
                              double feelsLike) {
        super(temperature, humidity, windSpeed, description, date, cityName);
        this.feelsLike = feelsLike;
    }

    @Override
    public String getDisplayTemperature() {
        return String.format("%.1f°C (Feels like %.1f°C)", temperature, feelsLike);
    }

    @Override
    public boolean isSevereWeather() {
        String lowerDesc = description.toLowerCase();
        return lowerDesc.contains("storm") || lowerDesc.contains("hurricane");
    }

    @Override
    public String getWeatherType() {
        return "CURRENT";
    }

    public double getFeelsLike() {
        return feelsLike;
    }
}