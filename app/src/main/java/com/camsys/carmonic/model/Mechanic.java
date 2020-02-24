package com.camsys.carmonic.model;

public class Mechanic {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String company;
    private double latitude;
    private double longitude;
    private String phone_number;
    private double starRating;

    private int estimatedDistanceInMins;

    public int getId() {
        return id;
    }

    public String getName() {
        return firstname;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public double getStarRating() {
        return starRating;
    }

    public int getEstimatedDistanceInMins() {
        return estimatedDistanceInMins;
    }
}
