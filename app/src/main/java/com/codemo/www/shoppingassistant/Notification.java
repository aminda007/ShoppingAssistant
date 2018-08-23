package com.codemo.www.shoppingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by aminda on 8/23/2018.
 */

public class Notification {

    private MainActivity activity;

    public Notification(MainActivity activity) {
        super();
        setActivity(activity);
    }

    public void showDeals(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Today Deals")
                .setMessage("25% off for Maliban Cream Cracker")
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

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }
}
