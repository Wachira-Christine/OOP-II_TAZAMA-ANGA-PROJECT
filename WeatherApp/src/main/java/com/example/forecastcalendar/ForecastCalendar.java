package com.example.forecastcalendar;

import com.example.model.WeatherData;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

// Implementation class implementing both interfaces
public class ForecastCalendar implements BasicForecastOperations, AdvancedForecastOperations {
    private final Map<DayOfWeek, WeatherData> forecastData;

    public ForecastCalendar() {
        this.forecastData = new HashMap<>();
    }

    @Override
    public void addForecastData(DayOfWeek day, WeatherData data) {
        forecastData.put(day, data);
    }

    @Override
    public WeatherData getForecastForDay(DayOfWeek day) {
        return forecastData.get(day);
    }

    @Override
    public Map<DayOfWeek, WeatherData> getAllForecasts() {
        return new HashMap<>(forecastData); // Return a copy to maintain encapsulation
    }
}