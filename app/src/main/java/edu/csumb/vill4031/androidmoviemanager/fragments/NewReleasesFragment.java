package edu.csumb.vill4031.androidmoviemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.csumb.vill4031.androidmoviemanager.R;
import edu.csumb.vill4031.androidmoviemanager.adapters.MovieReleaseAdapter;
import edu.csumb.vill4031.androidmoviemanager.models.Release;
import okhttp3.Headers;

public class NewReleasesFragment extends Fragment {
    public static final String TAG = "NewReleasesFragment";
    public static final String URL = "https://dvd-release-dates.herokuapp.com/this-week";

    List<Release> releases;

    public NewReleasesFragment() {
        // Required empty public constructor
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_releases, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvMovies = view.findViewById(R.id.rvMovies);

        releases = new ArrayList<>();

        // Create the adapter
        final MovieReleaseAdapter movieAdapter = new MovieReleaseAdapter(getContext(), releases);

        // Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));

        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("releases");
                    Log.i(TAG, "Releases: " + results.toString());

                    releases.addAll(Release.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + releases.size());

//                    for (int i = 0; i < releases.size(); i++) {
//                        String imdbId = releases.get(i).getIMDBId();
//                        String findURL = String.format("https://api.themoviedb.org/3/find/%s?api_key=%s&language=en-US&external_source=imdb_id", imdbId, KEY);
//                        Log.i(TAG, "URL: " + findURL);
//                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON Exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure" + statusCode);
            }
        });
    }
}