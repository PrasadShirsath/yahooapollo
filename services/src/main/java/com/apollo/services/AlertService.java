package com.apollo.services;

import com.apollo.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class AlertService {
    private final YahooEventReader reader;
    @Autowired
    public  AlertService(YahooEventReader reader){

        this.reader = reader;
    }

    final int high_severity = 5;
    final int moderate_severity= 3;
    final int low_moderity = 1;

    String[] priorities = {"High","Moderate","Low"};

    public Map<String,List<YahooEvent>> fetchActionableAlerts() throws IOException {
        List<YahooEvent> scrubbedEvents = reader.fetchScrubbedEvent();
        Map<String,List<YahooEvent>> eventsMap = new HashMap<String,List<YahooEvent>>();
        List<YahooEvent> highSeverityEvents = getEventsBySeverity(scrubbedEvents,high_severity);
        List<YahooEvent> moderateEvents = getEventsBySeverity(scrubbedEvents,moderate_severity);
        List<YahooEvent> lowPriorityEvents = getEventsBySeverity(scrubbedEvents,low_moderity);

        fillMap(eventsMap,priorities[0],highSeverityEvents);
        fillMap(eventsMap,priorities[1],moderateEvents);
        fillMap(eventsMap,priorities[2],moderateEvents);

        return eventsMap;
    }

    private List<YahooEvent> getEventsBySeverity(List<YahooEvent> events,int severity){
        return events.stream().filter(event -> event.getSeverity() == severity &&  event.getSeverity()<=severity+1).collect(Collectors.toList());
    }

    private void fillMap(Map<String, List<YahooEvent>> eventsMap,String priority,List<YahooEvent> events ){
        eventsMap.put(priority,events);
    }
}
