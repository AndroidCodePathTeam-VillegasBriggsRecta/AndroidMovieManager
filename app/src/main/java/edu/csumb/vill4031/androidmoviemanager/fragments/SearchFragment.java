package edu.csumb.vill4031.androidmoviemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import edu.csumb.vill4031.androidmoviemanager.adapters.MovieSearchAdapter;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;
import edu.csumb.vill4031.androidmoviemanager.models.Release;
import okhttp3.Headers;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";

    public static final String KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> searchResults;

    public SearchFragment() {
        // Required empty public constructor
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etInput = view.findViewById(R.id.etInput);
        Button searchBtn = view.findViewById(R.id.searchBtn);
        RecyclerView rvSearchRes = view.findViewById(R.id.rvSearchResults);

        searchResults = new ArrayList<>();

        // Create the adapter
        final MovieSearchAdapter movieAdapter = new MovieSearchAdapter(getContext(), searchResults);

        // Set the adapter on the recycler view
        rvSearchRes.setAdapter(movieAdapter);

        // Set a Layout Manager
        rvSearchRes.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = etInput.getText().toString();
                if (query.isEmpty()) {
                    Toast.makeText(getContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();

                String URL = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&language=en-US&query=%s&page=1&include_adult=false", KEY, query);

                final AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            Log.i(TAG, "Releases: " + results.toString());

                            searchResults.addAll(Movie.fromJsonArray(results));
                            movieAdapter.notifyDataSetChanged();
                            Log.i(TAG, "Movies: " + searchResults.size());
                        } catch (JSONException e) {
                            Log.e(TAG, "Hit JSON Exception", e);
                        }
                    }

                    @Override
                    public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                        Log.d(TAG, "onFailure" + i);
                    }
                });
            }
        });

    }
}