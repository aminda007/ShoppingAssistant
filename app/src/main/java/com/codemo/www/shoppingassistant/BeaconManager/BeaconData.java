package com.codemo.www.shoppingassistant.BeaconManager;

import android.util.Log;

import org.altbeacon.beacon.Beacon;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class BeaconData {

    private static final String TAG = "BeaconData";
    private static HashMap<Integer,Beacon> beacons=new HashMap<>();
    private static HashMap<Integer, BeaconWrapper> beaconWrapers=new HashMap<>();

    public static HashMap<Integer, Beacon> getBeacons() {
        return beacons;
    }

    public static void setBeacons(HashMap<Integer, Beacon> beacons) {
        BeaconData.beacons = beacons;
    }

    public static HashMap<Integer, BeaconWrapper> getBeaconWrapers() {
        return beaconWrapers;
    }

    public static void setBeaconWrapers(HashMap<Integer, BeaconWrapper> beaconWrapers) {
        BeaconData.beaconWrapers = beaconWrapers;
    }

    public static void showBeaconData(){
        String data="num of beacons:"+ beacons.size()+"\n";
        TreeMap<Integer,Beacon> treeMappedBeacons=new TreeMap<>(beacons);
        for(Map.Entry entry:treeMappedBeacons.entrySet()){
            data+="key: "+entry.getKey()+"  distance:"+((Beacon)entry.getValue()).getDistance()+"\n";
        }
        Log.i(TAG,data);
    }

    public static void addToHashMap(int i, Beacon beacon) {



        beacons.put(i,beacon);

        // TODO: 6/17/18 check if exixting add other attributes to the wraper
        BeaconWrapper wraper = new BeaconWrapper(beacon);
        wraper.setKey(i);
        beaconWrapers.put(i,wraper);

    }
}
