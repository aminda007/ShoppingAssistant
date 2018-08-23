package com.codemo.www.shoppingassistant.APICaller;

import android.os.AsyncTask;

import com.codemo.www.shoppingassistant.MainActivity;
import com.codemo.www.shoppingassistant.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by uwin5 on 08/23/18.
 */

public class BeaconList extends AsyncTask<String,Void,String []> {
    private MainActivity context;

    public BeaconList(MainActivity context) {
        this.context = context;
    }


    @Override
    protected String[] doInBackground(String... strings) {
        
        String login_url;
        try {

            login_url= context.getResources().getString(R.string.beaconListAPI);
            URL url =new URL(login_url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data= URLEncoder.encode("shop_id","UTF-8")+"="+URLEncoder.encode(strings[0],"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while ((line=bufferedReader.readLine())!=null){
                result+=line;

            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            String [] mainResult={result,strings[0]};
            return mainResult;

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{context.getResources().getString(R.string.NO_INTERNET)};
        }

        return null;
    }


    @Override
    protected void onPostExecute(String [] result) {
        super.onPostExecute(result);
        try {
            JSONArray obj = new JSONArray(result[0]);
            context.setBeacons(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    public MainActivity getContext() {
        return context;
    }

}