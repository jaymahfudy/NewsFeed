package com.dicoding.picodiploma.newsfeed.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.dicoding.picodiploma.newsfeed.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SourcesFragment sourcesFragment = new SourcesFragment();
        fragmentTransaction.replace(R.id.main_container,sourcesFragment,SourcesFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (getSupportFragmentManager().getBackStackEntryCount()>0)
            getSupportFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }
}
