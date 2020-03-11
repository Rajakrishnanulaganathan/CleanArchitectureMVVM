package com.rk.cleanarchitecture.di.module;

import com.rk.cleanarchitecture.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public  abstract  class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity mainActivity();
}
