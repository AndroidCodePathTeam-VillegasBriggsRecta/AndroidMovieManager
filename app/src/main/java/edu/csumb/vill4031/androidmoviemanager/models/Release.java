package edu.csumb.vill4031.androidmoviemanager.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Release {
    String imdbId;

    // Empty constructor needed by the Parceler library
    public  Release() {}

    public Release(JSONObject jsonObject) throws JSONException {
        imdbId = jsonObject.getString("imdbID");
    }

    public static List<Release> fromJsonArray(JSONArray releaseJsonArray) throws JSONException {
        List<Release> releases = new ArrayList<>();
        for (int i = 0; i < releaseJsonArray.length(); i++) {
            releases.add(new Release(releaseJsonArray.getJSONObject(i)));
        }

        return releases;
    }

    public String getIMDBId() {
        return imdbId;
    }
}
