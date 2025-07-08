package com.example.forecastcalendar;

import com.example.model.WeatherData;
import java.time.DayOfWeek;

public interface BasicForecastOperations {
    void addForecastData(DayOfWeek day, WeatherData data);
    WeatherData getForecastForDay(DayOfWeek day);
}
