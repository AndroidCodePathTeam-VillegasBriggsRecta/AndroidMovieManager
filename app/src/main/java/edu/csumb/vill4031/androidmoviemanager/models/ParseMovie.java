package edu.csumb.vill4031.androidmoviemanager.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import org.parceler.Parcel;

@ParseClassName("Movie")
@Parcel(analyze = ParseMovie.class)
public class ParseMovie extends ParseObject {
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMDB_ID = "imdb_id";
    public static final String KEY_RATING = "rating";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";

    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public void setTitle(String title) {
        put(KEY_TITLE, title);
    }

    public String getIMDbID() { return getString(KEY_IMDB_ID); }

    public void setIMDbID(String imdbId) { put(KEY_IMDB_ID, imdbId); }

    public float getRating() { return (float) getDouble(KEY_RATING); }

    public void setRating(float rating) { put(KEY_RATING, rating); }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public String getPosterPath() {
        return getString(KEY_POSTER_PATH);
    }

    public void setPosterPath(String posterPath) {
        put(KEY_POSTER_PATH, posterPath);
    }

    public String getBackdropPath() {
        return getString(KEY_BACKDROP_PATH);
    }

    public void setBackdropPath(String backdropPath) {
        put(KEY_BACKDROP_PATH, backdropPath);
    }
}