package com.rk.cleanarchitecture;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.databinding.FragmentMovieBinding;
import com.rk.cleanarchitecture.factory.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MovieFragment extends BaseFragment implements ClickListener {

    private PopularMovieAdapter popularMovieAdapter;
    private List<MovieEntity> articleArrayList = new ArrayList<>();
    private PopularMovieViewModel popularMovieViewmodel;
    private FragmentMovieBinding fragmentMovieBinding;

    @Inject
    ViewModelFactory viewModelFactory;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        popularMovieViewmodel = ViewModelProviders.of(this,viewModelFactory).get(PopularMovieViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMovieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);

        RecyclerView recyclerView = fragmentMovieBinding.popularMovieRecyclerview; // In xml we have given id rv_movie_list to RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2); // you can use getContext() instead of "this"
        recyclerView.setLayoutManager(layoutManager);
        popularMovieAdapter = new PopularMovieAdapter(getContext());
        intialapicall();
        return fragmentMovieBinding.getRoot();

    }
    private void intialapicall() {
        popularMovieViewmodel.init();
        popularMovieViewmodel.getMoviesResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                articleArrayList.clear();
                if(!movieEntities.isEmpty()){
                    articleArrayList.addAll(movieEntities);
                    popularMovieAdapter.setList(movieEntities);}
                setRecyclerViews();


            }
        });

    }


    private void setRecyclerViews() {
        fragmentMovieBinding.setMyAdapter(popularMovieAdapter);
        popularMovieAdapter.setonclicklistner(this);
    }


    @Override
    public void Onclick(int position) {
        Log.d("logmessage",String.valueOf(articleArrayList.get(position).getTitle()));
        ((MainActivity) getActivity()).switchDetailsFragment(articleArrayList.get(position).getId());


    }



}


