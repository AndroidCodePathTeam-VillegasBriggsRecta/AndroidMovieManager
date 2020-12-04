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

import org.parceler.Parcels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.csumb.vill4031.androidmoviemanager.R;
import edu.csumb.vill4031.androidmoviemanager.models.Release;

public class MovieReleaseAdapter extends RecyclerView.Adapter<MovieReleaseAdapter.ViewHolder>{
    Context context;
    List<Release> movies;

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
            tvIMDbID.setText(movie.getIMDBId());
            String imageUrl = movie.getPoster();
            Glide.with(context).load(imageUrl).into(ivPoster);

            // Register click listener on the whole row
//            container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // Navigate to a new activity on tap
//                    Intent i = new Intent(context, ClassName.class);
//                    i.putExtra("movie", Parcels.wrap(movie));
//                    context.startActivity(i);
//                }
//            });
        }
    }
}