package com.example.spacetogether.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String _id;
    private String userId;
    private String username;
    private List<Schedule> timetable;
    private List<String> friends;
    private List<String> friendsRequest;
    private List<User> friendsUser;
    private List<User> friendsRequestUser;

    public String getUserId() {
        return userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Schedule> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Schedule> timetable) {
        this.timetable = timetable;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriendsRequest() {
        return friendsRequest;
    }

    public void setFriendsRequest(List<String> friendsRequest) {
        this.friendsRequest = friendsRequest;
    }

    public List<User> getFriendsUser() {
        return friendsUser;
    }

    public void setFriendsUser(List<User> friendsUser) {
        this.friendsUser = friendsUser;
    }

    public List<User> getFriendsRequestUser() {
        return friendsRequestUser;
    }

    public void setFriendsRequestUser(List<User> friendsRequestUser) {
        this.friendsRequestUser = friendsRequestUser;
    }
}


class Schedule {
    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}