package com.rk.cleanarchitecture.di.module;

import com.rk.cleanarchitecture.MovieDetailsFragment;
import com.rk.cleanarchitecture.MovieFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MovieFragment contributeMovieFragment();

    @ContributesAndroidInjector
    abstract MovieDetailsFragment contributeMovieDetailsFragment();
}
