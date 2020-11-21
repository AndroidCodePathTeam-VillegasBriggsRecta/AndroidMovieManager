package edu.csumb.vill4031.androidmoviemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import edu.csumb.vill4031.androidmoviemanager.fragments.ListFragment;
import edu.csumb.vill4031.androidmoviemanager.fragments.NewReleasesFragment;
import edu.csumb.vill4031.androidmoviemanager.fragments.ProfileFragment;
import edu.csumb.vill4031.androidmoviemanager.models.Movie;
import edu.csumb.vill4031.androidmoviemanager.models.Release;
import okhttp3.Headers;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_new_releases:
                        fragment = new NewReleasesFragment();
                        break;
                    case R.id.action_lists:
                        fragment = new ListFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_new_releases);
    }
}