package com.example.spacetogether.data;

import java.io.Serializable;

public class User implements Serializable {
    private String useName;
    private String profileImageURL;

    public User(String useName, String profileImageURL) {
        this.useName = useName;
        this.profileImageURL = profileImageURL;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }
}
