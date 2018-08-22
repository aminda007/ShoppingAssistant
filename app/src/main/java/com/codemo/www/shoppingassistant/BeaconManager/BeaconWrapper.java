package com.codemo.www.shoppingassistant.BeaconManager;

import org.altbeacon.beacon.Beacon;

public class BeaconWrapper {

    private int key; //using major as the key

    private Beacon beacon;
    private double x;
    private double y;
    private long updatedtime;

    public BeaconWrapper(){

    }
    public BeaconWrapper(Beacon beacon){
        this.beacon=beacon;
    }
    //using major as the key
    public int getKey() {
        return key;
    }

    //using major as the key
    public void setKey(int key) {
        this.key = key;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public long getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(long updatedtime) {
        this.updatedtime = updatedtime;
    }
}
