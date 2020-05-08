package com.example.spacetogether.data;

import java.io.Serializable;
import java.util.List;

public class Meeting implements Serializable {
    private List<User> members;

    public Meeting(List<User> members) {
        this.members = members;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
