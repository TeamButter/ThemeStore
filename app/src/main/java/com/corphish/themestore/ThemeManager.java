package com.corphish.themestore;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Avinaba on 10/14/2016.
 */
public class ThemeManager {
    public ArrayList<Theme> mThemeArrayList = null;
    public JSONObject jsonObject = null;

    public ThemeManager(JSONObject jsonObject) { this.jsonObject = jsonObject; }

    public void parseJSON() {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("theme");
            for(int i = 0; i < jsonArray.length(); i++) {
                Theme theme = new Theme();
                JSONObject object = jsonArray.getJSONObject(i);

                theme.setName(object.getString("name"));
                theme.setDescription(object.getString("desc"));
                theme.setDownloadUrl(object.getString("download_url"));
                theme.setPackageName(object.getString("package"));
                theme.setBannerUrl(object.getString("banner_url"));
                theme.setThumbnailUrl(object.getString("thumbnail_url"));

                mThemeArrayList.add(theme);
            }
        } catch (JSONException e) {}
        Log.i(Constants.LOG_TAG, "JSON parsed, total entries - " + mThemeArrayList.size());
    }

    public ArrayList<Theme> getThemeArrayList() {return mThemeArrayList;}

    public int init() {
        Log.i(Constants.LOG_TAG, "Initializing ThemeManager...");

        // Check whether JSONObject is null or not
        if(jsonObject == null) {
            Log.e(Constants.LOG_TAG, "Got null JSON, bailing out!");
            return Constants.STATUS_FATAL;
        }

        // Initialize our themeArrayList
        mThemeArrayList = new ArrayList<Theme>();

        // Finally parse JSON to ThemeArrayList
        Log.i(Constants.LOG_TAG,"Parsing JSON");
        parseJSON();

        return Constants.STATUS_OK;
    }
}
