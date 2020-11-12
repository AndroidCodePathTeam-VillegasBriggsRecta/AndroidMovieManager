package edu.csumb.vill4031.androidmoviemanager;

import androidx.appcompat.app.AppCompatActivity;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;
import edu.csumb.vill4031.androidmoviemanager.models.Release;
import okhttp3.Headers;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String URL = "https://dvd-release-dates.herokuapp.com/this-week";

    //public static final String FIND_URL = String.format("https://api.themoviedb.org/3/find/%s?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&external_source=imdb_id", imdbId);

    List<Movie> movies;
    List<Release> releases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        releases = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("releases");
                    Log.i(TAG, "Releases: " + results.toString());

                    releases.addAll(Release.fromJsonArray(results));

                    Log.i(TAG, "Movies: " + releases.get(0).getIMDBId());

                    for (int i = 0; i < releases.size(); i++) {
                        String id = releases.get(i).getIMDBId();
                        Log.d(TAG, "From array: " + id);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON Exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}