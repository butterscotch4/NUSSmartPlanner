package com.example.myweeklycalender;

import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.util.ArrayList;
import java.util.List;

public class Global {
    public static List<CalendarEvent> eventList = new ArrayList<>();
    public static CalendarEvent refEvent; // this is used to pass events across intents
}
