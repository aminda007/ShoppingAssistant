package com.codemo.www.shoppingassistant;

import android.app.IntentService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.codemo.www.shoppingassistant.BeaconManager.BeaconData;
import com.codemo.www.shoppingassistant.LocationFindingAlgorithm.Coordinate2D;
import com.codemo.www.shoppingassistant.LocationFindingAlgorithm.LocationFinder;

import org.altbeacon.beacon.Beacon;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LocationUpdatingService {



    public static Coordinate2D currentLocation;

    public static float pointX=0;
    public static float pointY=0;


    static int beacon1Major=0;
    static int beacon2Major=0;
    static int beacon3Major=0;
    static HashMap<Integer,Coordinate2D> selectedBeaconCoordinates;

    static SharedPreferences sharedpreferences;
    /////////////////////////////
    private int delay=1000;  // delay to start after calling startTimer in ms
    private int period=2000; // period in ms
    /////////////////////////////


    private static final String TAG = "LocationUpdatingService";
    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();


    /*
     tasks needed to be run periodically
     */

    public void runPeriodically(){

        BeaconData.showBeaconData();

        HashMap<Integer, Beacon> allBeacons = BeaconData.getBeacons();

        double distanceFormB1=allBeacons.containsKey(beacon1Major)?allBeacons.get(beacon1Major).getDistance():0;
        double distanceFormB2=allBeacons.containsKey(beacon2Major)?allBeacons.get(beacon2Major).getDistance():0;
        double distanceFormB3=allBeacons.containsKey(beacon3Major)?allBeacons.get(beacon3Major).getDistance():0;

        currentLocation= LocationFinder.getLocation(selectedBeaconCoordinates.get(beacon1Major),
                selectedBeaconCoordinates.get(beacon2Major),selectedBeaconCoordinates.get(beacon3Major),
                distanceFormB1,distanceFormB2,distanceFormB3
        );

        if (!Double.isNaN(currentLocation.getX())){
            pointX=currentLocation.getX();
            pointY=currentLocation.getY();
        }

        Log.d(TAG,"point:"+currentLocation.toString());

    }



    public void init(Context context){
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);

        updateBeaconKeysAndCoordinates();

        timer=new Timer();
        initializeTimer();
        Log.d("lus","strted timer");
        startTimer();
    }

    private void initializeTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        runPeriodically();
                    }
                });

            }
        };
    }


    public void startTimer(){
        timer.schedule(timerTask,delay,period);
    }

    public void stopTimerTask() {
        if (timer != null)
            timer.cancel();
        timer = null;
    }

    private static HashMap<Integer, Coordinate2D> updateBeaconCoordinatesOfSelected3(){

        float b1x=sharedpreferences.contains(Constants.b1_x)? readSharedPreferences_float(Constants.b1_x):0;
        float b1y=sharedpreferences.contains(Constants.b1_y)? readSharedPreferences_float(Constants.b1_y):0;

        float b2x=sharedpreferences.contains(Constants.b2_x)? readSharedPreferences_float(Constants.b2_x):0;
        float b2y=sharedpreferences.contains(Constants.b2_y)? readSharedPreferences_float(Constants.b2_y):0;

        float b3x=sharedpreferences.contains(Constants.b3_x)? readSharedPreferences_float(Constants.b3_x):0;
        float b3y=sharedpreferences.contains(Constants.b3_y)? readSharedPreferences_float(Constants.b3_y):0;

        selectedBeaconCoordinates=new HashMap<>();

        selectedBeaconCoordinates.put(beacon1Major,new Coordinate2D(b1x,b1y));
        selectedBeaconCoordinates.put(beacon2Major,new Coordinate2D(b2x,b2y));
        selectedBeaconCoordinates.put(beacon3Major,new Coordinate2D(b3x,b3y));
        return selectedBeaconCoordinates;
    }


    public static void updateBeaconKeysAndCoordinates(){
        beacon1Major=sharedpreferences.contains(Constants.b1_major)? readSharedPreferences_int(Constants.b1_major):0;
        beacon2Major=sharedpreferences.contains(Constants.b2_major)? readSharedPreferences_int(Constants.b2_major):0;
        beacon3Major=sharedpreferences.contains(Constants.b3_major)? readSharedPreferences_int(Constants.b3_major):0;

        updateBeaconCoordinatesOfSelected3();
    }


    private static float readSharedPreferences_float(String key){
        float defaultValue=0;

        float val=sharedpreferences.getFloat(key,defaultValue);
        return val;
    }

    private static int readSharedPreferences_int(String key){
        int defaultValue=0;

        int val=sharedpreferences.getInt(key,defaultValue);
        return val;
    }

}
