package edu.csumb.vill4031.androidmoviemanager.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ParseMovie")
public class ParseMovie extends ParseObject {
    public static final String KEY_ID = "objectId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_YEAR = "year";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";

    public String getId() {
        return getString(KEY_ID);
    }

    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public void setTitle(String title) {
        put(KEY_TITLE, title);
    }

    public String getYear() {
        return getString(KEY_YEAR);
    }

    public void setYear(String year) {
        put(KEY_YEAR, year);
    }

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