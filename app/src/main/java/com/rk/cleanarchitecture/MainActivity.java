package com.rk.cleanarchitecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.factory.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {
    Toolbar toolbar;

    MovieFragment movieFragment ;
    @Inject
    ViewModelFactory viewModelFactory;

    PopularMovieViewmodel popularMovieViewmodel;

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
        moviefragment();

    }

    public void moviefragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_layout, movieFragment).commit();

    }

    public void moviedetailsfragment(int id) {
        Moviedetailsfragment moviedetailsfragment = new Moviedetailsfragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        moviedetailsfragment.setArguments(bundle);
        showFragment(moviedetailsfragment);
    }


    @Override
    public void onBackPressed() {
        hidebackarrow();
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment) {
        if (fragment instanceof Moviedetailsfragment) {
            showbackarrow();
        }
        else {
            hidebackarrow();
        }
        makeaddandreplacethefragment(fragment);

    }

    private void makeaddandreplacethefragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("second").replace(R.id.container_layout, fragment).commit();

    }

    public void showbackarrow(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void hidebackarrow(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }
}
