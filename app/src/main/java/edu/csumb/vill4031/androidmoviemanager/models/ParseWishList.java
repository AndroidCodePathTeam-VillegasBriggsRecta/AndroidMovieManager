package edu.csumb.vill4031.androidmoviemanager.models;

import android.util.Log;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcel;

@ParseClassName("Wish_List")
@Parcel(analyze = ParseWishList.class)
public class ParseWishList extends ParseObject{
    public static final String KEY_USER = "user_id";
    public static final String KEY_MOVIE = "movie_id";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public ParseMovie getMovie() {
        return getMovie();
    }

    public void setMovie(ParseMovie movie) {
        put(KEY_MOVIE, movie);
    }
}
