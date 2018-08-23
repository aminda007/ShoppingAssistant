package com.codemo.www.shoppingassistant;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.github.mikephil.charting.charts.ScatterChart;

import com.codemo.www.shoppingassistant.APICaller.BeaconList;
import com.codemo.www.shoppingassistant.APICaller.ItemList;
import com.codemo.www.shoppingassistant.APICaller.RackList;
import com.codemo.www.shoppingassistant.BeaconManager.BeaconData;
import com.codemo.www.shoppingassistant.BeaconManager.BeaconService;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

//import org.altbeacon.beacon.service.BeaconService;

public class MainActivity extends AppCompatActivity implements BeaconConsumer{

    private JSONArray beacons;
    private JSONArray racks;
    private JSONArray items;
    private String shopId = "1";

    private TextView mTextMessage;
    private ScatterChart chart;
    private static MarketMap map;
    private static Pointer beacon1, beacon2, beacon3, user;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final String TAG ="Main activity";
    private static ArrayList<Pointer> beaconList = new ArrayList<>();

    private BeaconManager beaconManager;
    private static final String TAG_BEACON_SCAN ="Beacon Scanner";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.INVISIBLE);
        // LineChart is initialized from xml
        chart = (ScatterChart) findViewById(R.id.chart);
        map = new MarketMap(chart);
        map.createMap();

        user = new Pointer();
        user.setX(5f);
        user.setY(10f);
        beacon1 = new Pointer();
        beacon1.setX(0f);
        beacon1.setY(0f);
        beacon1.setRadius(8f);
        beacon1.setId(1);
        beacon2 = new Pointer();
        beacon2.setX(3f);
        beacon2.setY(3f);
        beacon2.setRadius(5f);
        beacon2.setId(10035);
        beacon3 = new Pointer();
        beacon3.setX(0f);
        beacon3.setY(6f);
        beacon3.setRadius(8f);
        beacon2.setId(0);
        map.showBeacons(beacon1, beacon2, beacon3);


        beaconList.add(beacon1);
        beaconList.add(beacon2);
        beaconList.add(beacon3);
        user = new Trilateration().findCenter(beaconList);
        map.updateLocation(user);





    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        getPermission();
        fetchData();
        startBeaconScanningService();
        startLocationUpdatingService();


    }

    public void fetchData(){
        BeaconList bl = new BeaconList(this);
        bl.execute(shopId);

        RackList rl = new RackList(this);
        rl.execute(shopId);

        ItemList il = new ItemList(this);
        il.execute(shopId);

    }

    public void startBeaconScanningService() {
//        Intent i = new Intent(this, BeaconScannerService.class);
//        startService(i);
//        setupBeaconScanner();
        startService(new Intent(this, BeaconService.class));

    }

    public void startLocationUpdatingService() {
        new LocationUpdatingService().init(this);
    }

    public void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    //                    @Override 
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    private void setupBeaconScanner(){
        beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));




        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.bind(this);
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
        final HashMap<Integer,Beacon> beaconsmap=new HashMap<>();

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for(Beacon beacon : beacons) {
                    Log.d(TAG_BEACON_SCAN, "distance: " + beacon.getDistance() + " id:" + beacon.getId1() + "/" + beacon.getId2() + "/" + beacon.getId3());
//                    beaconsmap.put(beacon.getId2().toInt(),beacon);
                    BeaconData.addToHashMap(beacon.getId2().toInt(),beacon);
                    Log.d(TAG_BEACON_SCAN+"map","beacons:"+BeaconData.getBeacons().size());
                }
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
//        else beaconManager.bind(this);
        startService(new Intent(this, BeaconService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    public static void updateBeacons(Pointer bcn) {
        boolean found = false;
        int index = 0;

        for(int i=0; i<beaconList.size(); i++){
            if (beaconList.get(i).getId() == bcn.getId()){
                beaconList.get(i).setRadius(bcn.getRadius());
                found = true;
            }
        }

        if(!found && beaconList.size()<3){
            beaconList.add(bcn);
        }
        user = new Trilateration().findCenter(beaconList);
        map.updateLocation(user);
        Log.d(TAG_BEACON_SCAN+"map","beacons:1111111111111111111111111"+bcn.getRadius() + ".."+ bcn.getId());
    }

    public JSONArray getBeacons() {
        return beacons;
    }

    public void setBeacons(JSONArray beacons) {
        this.beacons = beacons;
    }

    public JSONArray getRacks() {
        return racks;
    }

    public void setRacks(JSONArray racks) {
        this.racks = racks;
    }

    public JSONArray getItems() {
        return items;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
