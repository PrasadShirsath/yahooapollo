package com.apollo.util;

import java.util.Calendar;


public class YahooEvent {
	
	int severity;
	int count;
	
	String node;
	String property;
	Calendar firstOccurence;
	
	String summary;
	
	Calendar latestTime;

    public int getSeverity() {
        return severity;
    }

    public int getCount() {
        return count;
    }

    public String getNode() {
        return node;
    }

    public String getProperty() {
        return property;
    }

    public Calendar getFirstOccurence() {
        return firstOccurence;
    }

    public String getSummary() {
        return summary;
    }

    public Calendar getLatestTime() {
        return latestTime;
    }
}
