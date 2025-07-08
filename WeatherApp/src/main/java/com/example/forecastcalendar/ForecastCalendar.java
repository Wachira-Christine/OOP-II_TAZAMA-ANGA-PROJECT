package com.example.forecastcalendar;

import com.example.model.WeatherData;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class ForecastCalendar {
    private final Map<DayOfWeek, WeatherData> forecastData;

    public ForecastCalendar() {
        this.forecastData = new HashMap<>();
    }

    public void addForecastData(DayOfWeek day, WeatherData data) {
        forecastData.put(day, data);
    }

    public WeatherData getForecastForDay(DayOfWeek day) {
        return forecastData.get(day);
    }

    public void printDailyForecast() {
        forecastData.forEach((day, data) -> {
            System.out.printf("%s: %s | Severe? %b%n",
                    day,
                    data.getDisplayTemperature(),
                    data.isSevereWeather());
        });
    }
}