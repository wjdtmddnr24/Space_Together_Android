package com.example.spacetogether.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String _id;
    private String userId;
    private String username;
    private List<Lecture> timetable;
    private List<String> friends;
    private List<String> friendsRequest;
    private List<User> friendsUser;
    private List<User> friendsRequestUser;
    private List<History> history;

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

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

    public List<Lecture> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Lecture> timetable) {
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

