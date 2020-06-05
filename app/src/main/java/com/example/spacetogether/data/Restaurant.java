package com.example.spacetogether.data;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String _id;
    private int id;
    private String name;
    private String image_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String get_id() {
        return _id;
    }
}
