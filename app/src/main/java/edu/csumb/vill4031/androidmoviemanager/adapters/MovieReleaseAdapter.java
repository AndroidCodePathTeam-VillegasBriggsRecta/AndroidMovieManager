package edu.csumb.vill4031.androidmoviemanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import edu.csumb.vill4031.androidmoviemanager.R;
import edu.csumb.vill4031.androidmoviemanager.fragments.MovieFragment;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;
import edu.csumb.vill4031.androidmoviemanager.models.ParseWishList;
import edu.csumb.vill4031.androidmoviemanager.models.Release;
import okhttp3.Headers;

public class MovieReleaseAdapter extends RecyclerView.Adapter<MovieReleaseAdapter.ViewHolder> {
    Context context;
    List<Release> movies;
    List<ParseWishList> wishLists;

    public static final String TAG = "MovieReleaseAdapter";
    public static final String KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public MovieReleaseAdapter(Context context, List<Release> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Inflates a layout from XML and returns the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Populates data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the position passed in
        Release movie = movies.get(position);
        // Bind the movie data into the ViewHolder
        holder.bind(movie);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvIMDbID;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvIMDbID = itemView.findViewById(R.id.tvIMDbID);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Release movie) {
            tvTitle.setText(movie.getTitle());
            tvIMDbID.setText(String.format("IMDb ID: %s", movie.getIMDBId()));
            String imageUrl = movie.getPoster();
            Glide.with(context).load(imageUrl).into(ivPoster);

            // Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    String URL = String.format("https://api.themoviedb.org/3/find/%s?api_key=%s&language=en-US&external_source=imdb_id", tvIMDbID.getText().toString(), KEY);

                    final AsyncHttpClient client = new AsyncHttpClient();
                    client.get(URL, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Headers headers, JSON json) {
                            Log.d(TAG, "onSuccess");

                            JSONObject jsonObject = json.jsonObject;
                            try {
                                JSONArray results = jsonObject.getJSONArray("movie_results");
                                List<Movie> movies = Movie.fromJsonArray(results);
                                
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Fragment fragment = MovieFragment.newInstance(movies.get(0));
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
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
}
