package com.example.forecastcalendar;

import com.example.model.WeatherData;

import java.time.DayOfWeek;
import java.util.Map;

// Extended interface for advanced operations
public interface AdvancedForecastOperations {
    Map<DayOfWeek, WeatherData> getAllForecasts();
}
