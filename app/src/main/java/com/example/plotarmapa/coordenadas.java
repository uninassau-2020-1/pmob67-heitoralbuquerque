package com.example.plotarmapa;

import android.app.Application;

public class coordenadas extends Application {

    private double latitude;
    private double longitude;

    public coordenadas(){

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
