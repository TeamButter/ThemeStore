package com.corphish.themestore;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Avinaba on 10/14/2016.
 */
public class JSONFetcher extends AsyncTask<String, String, String> {

    JSONObject jsonObject = null;
    String JSONStr = null;

    String TAG = Constants.LOG_TAG;
    String url = Constants.JSON_Url;

    boolean isJSONFetched = false;

    public interface JSONResponse {
        void onResponse(JSONObject jsonObject);
    }

    JSONResponse jsonResponse = null;

    public JSONFetcher(JSONResponse response) {
        this.jsonResponse = response;
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        JSONStr = new String();
        JSONStr = makeServiceCall(url);
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
       try {
           jsonObject = new JSONObject(JSONStr);
           isJSONFetched = true;
           jsonResponse.onResponse(jsonObject);
       } catch (JSONException e) {
           Log.e(TAG,"Failed to process JSON!");
       } catch (NullPointerException e) {
           Log.e(TAG,"Could not fetch JSON data");
       }

    }

}
