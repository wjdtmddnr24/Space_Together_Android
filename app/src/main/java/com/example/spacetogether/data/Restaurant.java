package com.example.spacetogether.data;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String restaurantName;
    private String restaurantImageURL;

    public Restaurant(int id, String restaurantName, String restaurantImageURL) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantImageURL = restaurantImageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImageURL() {
        return restaurantImageURL;
    }

    public void setRestaurantImageURL(String restaurantImageURL) {
        this.restaurantImageURL = restaurantImageURL;
    }
}
