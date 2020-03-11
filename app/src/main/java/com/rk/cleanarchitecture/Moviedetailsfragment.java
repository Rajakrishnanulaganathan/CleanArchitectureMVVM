package com.rk.cleanarchitecture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.databinding.ActivityMovieDetailsBinding;
import com.rk.cleanarchitecture.factory.ViewModelFactory;
import com.rk.cleanarchitecture.utils.Constants;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class Moviedetailsfragment extends BaseFragment {

    ActivityMovieDetailsBinding activityMovieDetailsBinding;
    PopularMovieViewmodel popularMovieGetViewmodel;

    @Inject
    ViewModelFactory viewModelFactory;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        popularMovieGetViewmodel = ViewModelProviders.of(this,viewModelFactory).get(PopularMovieViewmodel.class);    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityMovieDetailsBinding= DataBindingUtil.inflate(inflater,R.layout.activity_movie_details,container,false);
        View view=activityMovieDetailsBinding.getRoot();
        Bundle bundle=this.getArguments();
        popularMovieGetViewmodel.getmoviedetails(String.valueOf(bundle.getInt("id")));
        popularMovieGetViewmodel.getMoviesResponseDetailsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<MovieEntity>() {
            @Override
            public void onChanged(MovieEntity movieEntity) {
                activityMovieDetailsBinding.setMovie(movieEntity);
                addMovieDetails(movieEntity);
            }
        });


        return view;
    }
    private void addMovieDetails(MovieEntity movieEntity) {
        Picasso.get().load(Constants.IMAGE_ENDPOINT_PREFIX+movieEntity.getPosterPath()).into(activityMovieDetailsBinding.imageViewCover);

    }

}
