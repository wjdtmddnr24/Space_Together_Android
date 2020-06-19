package com.example.spacetogether.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class History implements Serializable {
    List<String> meeting;
    List<User> meetingUser;
    String restaurantInfo;
    Restaurant restaurantInfoRestaurant;
    Date visitedAt;

    public List<String> getMeeting() {
        return meeting;
    }

    public void setMeeting(List<String> meeting) {
        this.meeting = meeting;
    }

    public List<User> getMeetingUser() {
        return meetingUser;
    }

    public void setMeetingUser(List<User> meetingUser) {
        this.meetingUser = meetingUser;
    }

    public String getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(String restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public Restaurant getRestaurantInfoRestaurant() {
        return restaurantInfoRestaurant;
    }

    public void setRestaurantInfoRestaurant(Restaurant restaurantInfoRestaurant) {
        this.restaurantInfoRestaurant = restaurantInfoRestaurant;
    }

    public Date getVisitedAt() {
        return visitedAt;
    }

    public void setVisitedAt(Date visitedAt) {
        this.visitedAt = visitedAt;
    }
}
