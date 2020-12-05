package edu.csumb.vill4031.androidmoviemanager.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.csumb.vill4031.androidmoviemanager.LoginActivity;
import edu.csumb.vill4031.androidmoviemanager.R;
import edu.csumb.vill4031.androidmoviemanager.adapters.MovieReleaseAdapter;
import edu.csumb.vill4031.androidmoviemanager.adapters.MovieSearchAdapter;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;
import edu.csumb.vill4031.androidmoviemanager.models.ParseMovie;
import edu.csumb.vill4031.androidmoviemanager.models.ParseWishList;
import edu.csumb.vill4031.androidmoviemanager.models.Release;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    public static final String URL = "https://dvd-release-dates.herokuapp.com/this-week";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Movie> wishList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = view.findViewById(R.id.myToolbar);
        activity.setSupportActionBar(toolbar);

        RecyclerView rvWishList = view.findViewById(R.id.rvWishList);

        wishList = new ArrayList<>();

        // Create the adapter
        final MovieSearchAdapter wishListAdapter = new MovieSearchAdapter(getContext(), wishList);

        // Set the adapter on the recycler view
        rvWishList.setAdapter(wishListAdapter);

        // Set a Layout Manager
        rvWishList.setLayoutManager(new LinearLayoutManager(getContext()));

        wishList.addAll(fetchWishList());
        wishListAdapter.notifyDataSetChanged();

        Log.i(TAG, "WISHLIST SIZE: " + String.valueOf(wishList.size()));

        for (Movie movie : wishList) {
            Log.i(TAG, movie.getTitle());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                Log.i(TAG,"Logout button in Action Bar clicked.");
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                startActivity(i);
                Toast.makeText(getContext(), "Signed Ouut", Toast.LENGTH_SHORT).show();
            }
        });
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


//        displayWishList();

//        private void queryWishList(){
//            ParseQuery<ParseWishList> query = ParseQuery.getQuery(ParseWishList.class);
//            query.findInBackground(new FindCallback<ParseWishList>() {
//                @Override
//                public void done(List<ParseWishList> objects, ParseException e) {
//                    if(e != null) {
//                        Log.e(TAG, "Issue with getting wishlist");
//                        return;
//                    }
//                    for(ParseWishList object : objects){
//                        Log.i(TAG, object.getUser().getUsername() + " " + object.getMovie().getTitle());
//                        movies.add(object.getMovie());
//                    }
//                }
//            });
//        }

//        final AsyncHttpClient client = new AsyncHttpClient();
//        client.get(URL, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG, "onSuccess");
//                JSONObject jsonObject = json.jsonObject;
//                try {
//                    JSONArray results = jsonObject.getJSONArray("releases");
//                    Log.i(TAG, "Wishlist: " + results.toString());
//
//                    wishList.addAll(Movie.fromJsonArray(results));
//                    wishListAdapter.notifyDataSetChanged();
//                    Log.i(TAG, "Movies: " + wishList.size());
//                } catch (JSONException e) {
//                    Log.e(TAG, "Hit JSON Exception", e);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//                Log.d(TAG, "onFailure" + statusCode);
//            }
//        });

//    public void displayWishList() {
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Wish_List");
//        query.whereEqualTo("user_id", ParseUser.getCurrentUser());
//
//        ParseQuery<ParseMovie> movieQuery = ParseQuery.getQuery("Movie");
//
//        try{
//            List<ParseObject> results = query.find();
//            for(ParseObject result : results){
//                Log.i(TAG, "Object found " + result.getObjectId());
//                Log.i(TAG, result.get("movie_id").toString());
//                ParseObject obj = (ParseObject) result.get("movie_id");
//                Log.i(TAG, obj.getObjectId());
//
//                try{
//                    List<ParseMovie> res = movieQuery.find();
//                    for(ParseMovie r: res) {
//                        if(r.getObjectId().equals(obj.getObjectId())){
//                            Log.i(TAG, "In database" + r.getTitle());
//                        }
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}