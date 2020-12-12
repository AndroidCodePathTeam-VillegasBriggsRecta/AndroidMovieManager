package edu.csumb.vill4031.androidmoviemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.csumb.vill4031.androidmoviemanager.R;
import edu.csumb.vill4031.androidmoviemanager.adapters.MovieReleaseAdapter;
import edu.csumb.vill4031.androidmoviemanager.adapters.MovieSearchAdapter;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;
import edu.csumb.vill4031.androidmoviemanager.models.ParseMovie;
import edu.csumb.vill4031.androidmoviemanager.models.Release;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    public static final String TAG = "ListFragment";

    List<Movie> catalog;

    public ListFragment() {
        // Required empty public constructor
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Switch simpleSwitch = (Switch) view.findViewById(R.id.listSwitch);

        final TextView tvListName = view.findViewById(R.id.tvListName);

        RecyclerView rvMovies = view.findViewById(R.id.rvMovieList);

        simpleSwitch.setText("Catalog");

        tvListName.setText("Catalog");

        catalog = new ArrayList<>();

        // Create the adapter
        final MovieSearchAdapter movieAdapter = new MovieSearchAdapter(getContext(), catalog);

        // Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));

        catalog.addAll(fetchCatalog());
        tvListName.setText(String.format("%s Movies in Catalog", catalog.size()));
        movieAdapter.notifyDataSetChanged();

        Log.i(TAG, "CATALOG SIZE: " + String.valueOf(catalog.size()));

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.i(TAG, "show wish list");
                    simpleSwitch.setText("Wish List");
                    catalog.clear();
                    catalog.addAll(fetchWishList());
                    tvListName.setText(String.format("%s Movies in Wish List", catalog.size()));
                } else {
                    Log.i(TAG, "not checked!");
                    simpleSwitch.setText("Catalog");
                    catalog.clear();
                    catalog.addAll(fetchCatalog());
                    tvListName.setText(String.format("%s Movies in Catalog", catalog.size()));
                }
                movieAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<Movie> fetchCatalog() {
        List<Movie> movies = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Catalog");
        query.whereEqualTo("user_id", ParseUser.getCurrentUser());

        ParseQuery<ParseMovie> movieQuery = ParseQuery.getQuery("Movie");

        try {
            List<ParseObject> results = query.find();
            for (ParseObject result : results) {
                ParseObject obj = (ParseObject) result.get("movie_id");

                try {
                    List<ParseMovie> res = movieQuery.find();
                    for (ParseMovie r : res) {
                        if (r.getObjectId().equals(obj.getObjectId())) {
                            Movie m = new Movie();

                            m.setMovieId(r.getIMDbID());
                            m.setBackdropPath(r.getBackdropPath());
                            m.setOverview(r.getDescription());
                            m.setRating(r.getRating());
                            m.setPosterPath(r.getPosterPath());
                            m.setTitle(r.getTitle());

                            movies.add(m);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private List<Movie> fetchWishList() {
        List<Movie> movies = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Wish_List");
        query.whereEqualTo("user_id", ParseUser.getCurrentUser());

        ParseQuery<ParseMovie> movieQuery = ParseQuery.getQuery("Movie");

        try {
            List<ParseObject> results = query.find();
            for (ParseObject result : results) {
                ParseObject obj = (ParseObject) result.get("movie_id");

                try {
                    List<ParseMovie> res = movieQuery.find();
                    for (ParseMovie r : res) {
                        if (r.getObjectId().equals(obj.getObjectId())) {
                            Movie m = new Movie();

                            m.setMovieId(r.getIMDbID());
                            m.setBackdropPath(r.getBackdropPath());
                            m.setOverview(r.getDescription());
                            m.setRating(r.getRating());
                            m.setPosterPath(r.getPosterPath());
                            m.setTitle(r.getTitle());

                            movies.add(m);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return movies;
    }
}