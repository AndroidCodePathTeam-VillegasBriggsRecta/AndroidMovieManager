package edu.csumb.vill4031.androidmoviemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

import edu.csumb.vill4031.androidmoviemanager.R;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;
import edu.csumb.vill4031.androidmoviemanager.models.ParseCatalog;
import edu.csumb.vill4031.androidmoviemanager.models.ParseMovie;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    public static final String TAG = "MovieFragment";
    // the fragment initialization parameter
    private static final String ARG_PARAM1 = "param1";

    private Movie mParam1;


    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(Movie param1) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putSerializable("movie", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Movie) getArguments().getSerializable("movie");
            Log.i(TAG, "Releases: " + mParam1.getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ivPoster = view.findViewById(R.id.ivPoster);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView tvOverview = view.findViewById(R.id.tvOverview);

        String imageUrl = mParam1.getPosterPath();
        Glide.with(getContext()).load(imageUrl).into(ivPoster);

        tvTitle.setText(mParam1.getTitle());
        ratingBar.setRating((float) mParam1.getRating());
        tvOverview.setText(mParam1.getOverview());

        Button catalogBtn = view.findViewById(R.id.catalogBtn);
        Button wishlistBtn = view.findViewById(R.id.wishlistBtn);

        catalogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Adding to Catalog", Toast.LENGTH_SHORT).show();
                addToCatalog(mParam1);
            }
        });

        wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Adding to Wishlist", Toast.LENGTH_SHORT).show();
                fetchCatalog();
                addToWishList(mParam1);
            }
        });
    }

    public void fetchCatalog() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Catalog");
        query.whereEqualTo("user_id", ParseUser.getCurrentUser());

        ParseQuery<ParseMovie> movieQuery = ParseQuery.getQuery("Movie");

        try {
            List<ParseObject> results = query.find();
            for (ParseObject result : results) {
                Log.i(TAG, "Object found " + result.getObjectId());
                Log.i(TAG, result.get("movie_id").toString());
                ParseObject obj = (ParseObject) result.get("movie_id");
                Log.i(TAG, obj.getObjectId());

                // Fetches data synchronously
                try {
                    List<ParseMovie> res = movieQuery.find();
                    for (ParseMovie r : res) {
                        if (r.getObjectId().equals(obj.getObjectId())) {
                            Log.i(TAG, "IN DATABASE" + r.getTitle());
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void addToCatalog(Movie movie) {
        // Creates a new ParseQuery object to help us fetch Movie objects
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");

        // Fetches data synchronously
        try {
            final ParseMovie parseMovie;
            List<ParseObject> results = query.find();
            for (ParseObject result : results) {
                final String title = (String) result.get("title");
                if (title.equals(movie.getTitle())) {
                    parseMovie = (ParseMovie) result;
                    Log.i(TAG, "already in db" + parseMovie.getTitle());

                    ParseObject entity = new ParseObject("Catalog");
                    entity.put("user_id", ParseUser.getCurrentUser());
                    entity.put("movie_id", parseMovie);
                    entity.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Log.e(TAG, "Error while saving in catalog", e);
                                Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.i(TAG, "Catalog save was successful");

                        }
                    });
                    return;
                }

            }
            parseMovie = new ParseMovie();

            parseMovie.setTitle(movie.getTitle());
            parseMovie.setIMDbID(String.valueOf(movie.getMovieId()));
            parseMovie.setDescription(movie.getOverview());
            parseMovie.setPosterPath(movie.getPosterPath());
            parseMovie.setBackdropPath(movie.getBackdropPath());
            parseMovie.setRating((float) movie.getRating());

            parseMovie.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null){
                        Log.e(TAG, "Error while saving", e);
                        Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.i(TAG, "Movie save was successful");

                    ParseObject entity = new ParseObject("Catalog");
                    entity.put("user_id", ParseUser.getCurrentUser());
                    entity.put("movie_id", parseMovie);
                    entity.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Log.e(TAG, "Error while saving in catalog", e);
                                Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.i(TAG, "Catalog save was successful");

                        }
                    });

                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void addToWishList(Movie movie) {
        // Creates a new ParseQuery object to help us fetch Movie objects
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");

        // Fetches data synchronously
        try {
            final ParseMovie parseMovie;
            List<ParseObject> results = query.find();
            for (ParseObject result : results) {
                final String title = (String) result.get("title");
                if (title.equals(movie.getTitle())) {
                    parseMovie = (ParseMovie) result;
                    Log.i(TAG, "already in db" + parseMovie.getTitle());

                    ParseObject entity = new ParseObject("Wish_List");
                    entity.put("user_id", ParseUser.getCurrentUser());
                    entity.put("movie_id", parseMovie);
                    entity.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Log.e(TAG, "Error while saving in wishlist", e);
                                Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.i(TAG, "Wislist save was successful");

                        }
                    });
                    return;
                }

            }
            parseMovie = new ParseMovie();

            parseMovie.setTitle(movie.getTitle());
            parseMovie.setIMDbID(String.valueOf(movie.getMovieId()));
            parseMovie.setDescription(movie.getOverview());
            parseMovie.setPosterPath(movie.getPosterPath());
            parseMovie.setBackdropPath(movie.getBackdropPath());
            parseMovie.setRating((float) movie.getRating());

            parseMovie.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null){
                        Log.e(TAG, "Error while saving", e);
                        Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.i(TAG, "Movie save was successful");

                    ParseObject entity = new ParseObject("Wish_List");
                    entity.put("user_id", ParseUser.getCurrentUser());
                    entity.put("movie_id", parseMovie);
                    entity.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Log.e(TAG, "Error while saving in wishlilst", e);
                                Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.i(TAG, "Catalog save was successful");

                        }
                    });

                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}