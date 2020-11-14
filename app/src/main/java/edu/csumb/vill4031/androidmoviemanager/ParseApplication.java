package edu.csumb.vill4031.androidmoviemanager;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import edu.csumb.vill4031.androidmoviemanager.models.ParseMovie;

public class ParseApplication extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(ParseMovie.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Ys8S0etc0ioCaJob3Fx0YG8m1qGEs55QtOVTHAE0")
                .clientKey("huP53BDhsbN6MXMIlRIyfSlyINkAdiAUJaEDXPsd")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}