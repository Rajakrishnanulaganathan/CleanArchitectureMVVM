package com.rk.cleanarchitecture;

import android.app.Activity;
import android.app.Application;


import androidx.fragment.app.Fragment;

import com.rk.cleanarchitecture.di.component.DaggerApiComponent;
import com.rk.cleanarchitecture.di.module.ApiModule;
import com.rk.cleanarchitecture.di.module.DBModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MovieApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
       DaggerApiComponent.builder().application(this).apimodule(new ApiModule()).dbmodule(new DBModule()).build().inject(this);
    }


    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }
}
