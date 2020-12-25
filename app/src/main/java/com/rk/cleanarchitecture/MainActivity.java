package com.rk.cleanarchitecture;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rk.cleanarchitecture.factory.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {
    Toolbar toolbar;

    MovieFragment movieFragment;
    @Inject
    ViewModelFactory viewModelFactory;

    PopularMovieViewModel popularMovieViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle("Movies");
        movieFragment = new MovieFragment();


        AndroidInjection.inject(this);
        switchMovieFragment();

    }

    public void switchMovieFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_layout, movieFragment).commit();

    }

    public void switchDetailsFragment(int id) {
        MovieDetailsFragment moviedetailsfragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        moviedetailsfragment.setArguments(bundle);
        showFragment(moviedetailsfragment);
    }


    @Override
    public void onBackPressed() {
        hideBackArrow();
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment) {
        if (fragment instanceof MovieDetailsFragment) {
            showBackArrow();
        } else {
            hideBackArrow();
        }
        replaceFragment(fragment);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("second").replace(R.id.container_layout, fragment).commit();

    }

    public void showBackArrow() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void hideBackArrow() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }
}
