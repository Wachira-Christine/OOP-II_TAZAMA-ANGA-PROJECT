package com.example.model;

import java.time.LocalDateTime;

public class WeatherData {
    private final double temperature; // in Celsius
    private final int humidity;
    private final double windSpeed; // in meters per second
    private final String description;
    private final LocalDateTime date;
    private final String cityName;

    // Protected constructor for extensibility
    protected WeatherData(double temperature, int humidity, double windSpeed,
                          String description, LocalDateTime date, String cityName) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.description = description;
        this.date = date;
        this.cityName = cityName;
    }

    // Factory method for basic weather data
    public static WeatherData createBasic(double temperature, int humidity,
                                          double windSpeed, String description,
                                          LocalDateTime date, String cityName) {
        return new WeatherData(temperature, humidity, windSpeed,
                description, date, cityName);
    }

    // Getters only - immutable core properties
    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCityName() {
        return cityName;
    }

    // Temperature conversion - can be overridden by subclasses
    public double getTemperatureInCelsius() {
        return temperature;
    }

    public double getTemperatureInFahrenheit() {
        return (temperature * 9 / 5) + 32;
    }

    // Weather condition analysis - can be overridden by subclasses
    public boolean isPrecipitationLikely() {
        String lowerDesc = description.toLowerCase();
        return lowerDesc.contains("rain") ||
                lowerDesc.contains("storm") ||
                lowerDesc.contains("shower");
    }

    @Override
    public String toString() {
        return String.format(
                "WeatherData{temperature=%.1f°C, humidity=%d%%, windSpeed=%.1f m/s, " +
                        "description='%s', date=%s, cityName='%s'}",
                temperature, humidity, windSpeed, description, date, cityName
        );
    }
}
