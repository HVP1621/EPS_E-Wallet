package com.journaldev.iitbhilaieps;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.BufferedWriter;
import  java.io.OutputStream;
import  java.io.OutputStreamWriter;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class GetBase64String {
    public static String getBase64StringFromURL(String urlString, String recipientID, String amount, String authToken) throws IOException{
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
//        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setRequestProperty("Content-Type","application/json");
        urlConnection.setRequestProperty("Accept-Encoding","identity");
//        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();


        JSONObject jsonParam = new JSONObject();
        try{
            jsonParam.put("vendorid",recipientID);
            jsonParam.put("amount",amount);
            jsonParam.put("auth_token",authToken);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        DataOutputStream data = new DataOutputStream(urlConnection.getOutputStream());

        data.write(jsonParam.toString().getBytes());

        data.flush();

        data.close();

//        InputStream is = urlConnection.getInputStream();
//        byte[] response = new byte[65536];
//        is.read(response);
//        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//        Use getinputstream for above as openstream sends another http request. Below is the correct implementation
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
//        Log.i("VAL: ",response.toString());
        urlConnection.disconnect();
//        return response;
        return sb.toString();
    }
}
