package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class WeatherData {
    protected final double temperature; // in Celsius
    protected final int humidity;
    protected final double windSpeed; // in m/s
    protected final String description;
    protected final LocalDateTime date;
    protected final String cityName;

    protected WeatherData(double temperature, int humidity, double windSpeed,
                          String description, LocalDateTime date, String cityName) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.description = description;
        this.date = date;
        this.cityName = cityName;
    }

    // Common getters
    public double getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
    public String getCityName() { return cityName; }

    // Polymorphic methods
    public abstract String getDisplayTemperature();
    public abstract boolean isSevereWeather();
    public abstract String getWeatherType();
}
