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

import org.parceler.Parcels;

import edu.csumb.vill4031.androidmoviemanager.R;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;

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
            }
        });

        wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Adding to Wishlist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}