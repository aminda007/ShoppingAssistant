package com.codemo.www.shoppingassistant;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.codemo.www.shoppingassistant.MainActivity;
import com.codemo.www.shoppingassistant.Pointer;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.HashMap;

public class BeaconService extends Service implements BeaconConsumer {

    private static boolean unlock;

    public BeaconService() {
    }


    private BeaconManager beaconManager;
    private static final String TAG_BEACON_SCAN ="Beacon Scanner";
    private static Runnable handlerTask;


    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = BeaconManager.getInstanceForApplication(this);
//
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));
//
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));




        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.bind(this);
        startTimer();
    }

    public static void startTimer(){
        final Handler handler = new Handler();
        handlerTask = new Runnable()
        {
            @Override
            public void run() {
                unlock = true;
                handler.postDelayed(handlerTask, 2000);
            }
        };
        handlerTask.run();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onBeaconServiceConnect() {

//        Identifier.parse("B9407F30-F5F8-466E-AFF9-25556B57FE6D")
        final Region region = new Region("myBeaons",null, null, null);

        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                try {
                    Log.d(TAG_BEACON_SCAN, "didEnterRegion");
                    beaconManager.startRangingBeaconsInRegion(region);
                    beaconManager.startMonitoringBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                try {
                    Log.d(TAG_BEACON_SCAN, "didExitRegion");
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });
//
//        final HashMap<Integer,Beacon> beaconsmap=new HashMap<>();

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for(Beacon beacon : beacons) {
                    Pointer bcn = new Pointer();
                    bcn.setRadius((float)beacon.getDistance());
                    bcn.setId(Integer.valueOf(beacon.getId2().toString()));
                    if(unlock){
                        Log.d(TAG_BEACON_SCAN, "distance: " + beacon.getDistance() + " id:" + beacon.getId1() + "/" + beacon.getId2() + "/" + beacon.getId3());
                        MainActivity.updateBeacons(bcn);
                        Log.d(TAG_BEACON_SCAN,"beacons:"+beacons.size());
                    }

                }
                unlock = false;
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        beaconManager.unbind(this);
    }
}
