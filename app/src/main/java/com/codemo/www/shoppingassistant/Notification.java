package com.codemo.www.shoppingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aminda on 8/23/2018.
 */

public class Notification {

    private MainActivity activity;
    private ArrayList<Integer> rackIdList = new ArrayList<>();

    public Notification(MainActivity activity) {
        super();
        setActivity(activity);
    }

    public void showDeals(int id){
        String msg = findDeals(id);
        if(findId(id)){
            return;
        }
        rackIdList.add(id);
        if(msg == ""){
            return;
        }
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Today Deals")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                        Intent myIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        startActivity(myIntent);
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", mainActivity.getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
                    }
                });
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//
//                    }
//                });
        dialog.show();
    }

    public boolean findId(int id){
        for (int i =0; i< rackIdList.size(); i++){
            if(id == rackIdList.get(i)){
                return true;
            }
        }
        return false;
    }

    public String findDeals(int id){
        JSONArray  list = activity.getItems();
        String msg = "";
        for(int i=0; i<list.length(); i++){
            try {
                JSONObject jo =  (JSONObject)list.get(i);
                int rid = jo.getInt("rackId");
                if(rid==id && !jo.isNull("offer")){
                    JSONObject jj = (JSONObject) jo.get("offer");
                    msg = jj.getString("description");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  msg;
    }

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }
}
