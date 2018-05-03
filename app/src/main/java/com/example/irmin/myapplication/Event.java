package com.example.irmin.myapplication;

/**
 * Created by irmin on 2018-04-30.
 */


public class Event {

    String userID;
    String eventTitle;
    String eventContent;
    String startTime;
    String closeTime;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public Event(String userID, String eventTitle, String eventContent, String startTime, String closeTime) {
        this.userID = userID;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.startTime = startTime;
        this.closeTime = closeTime;
    }
}
