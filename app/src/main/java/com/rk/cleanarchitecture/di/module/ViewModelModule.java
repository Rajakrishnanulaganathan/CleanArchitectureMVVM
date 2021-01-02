package com.rk.cleanarchitecture.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rk.cleanarchitecture.PopularMovieViewModel;
import com.rk.cleanarchitecture.factory.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(PopularMovieViewModel.class)
    protected abstract ViewModel movieListViewModel(PopularMovieViewModel popularMovieViewmodel);
}
