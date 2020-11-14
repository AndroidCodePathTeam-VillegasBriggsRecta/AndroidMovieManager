package edu.csumb.vill4031.androidmoviemanager;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.csumb.vill4031.androidmoviemanager.models.Release;
import okhttp3.Headers;

public class DVDRelDateClient {
    public static final String TAG = "DVDRelDateClient";
    public static final String LAST_WEEKS_RELEASES_URL = "https://dvd-release-dates.herokuapp.com/last-week";
    public static final String THIS_WEEKS_RELEASES_URL = "https://dvd-release-dates.herokuapp.com/this-week";
    public static final String NEXT_WEEKS_RELEASES_URL = "https://dvd-release-dates.herokuapp.com/next-week";
    public static final String IN_TWO_WEEKS_RELEASES_URL = "https://dvd-release-dates.herokuapp.com/in-two-weeks";

    public  DVDRelDateClient() {}

    AsyncHttpClient client = new AsyncHttpClient();

    List<Release> releases;

    public List<Release> getThisWeeksReleases() {
        releases = new ArrayList<>();

        client.get(THIS_WEEKS_RELEASES_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JsonHttpResponseHandler.JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("releases");
                    Log.i(TAG, "Releases: " + results.toString());

                    releases.addAll(Release.fromJsonArray(results));
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON Exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        return releases;
    }

}
