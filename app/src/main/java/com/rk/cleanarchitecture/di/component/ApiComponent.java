package com.rk.cleanarchitecture.di.component;

import android.app.Application;

import com.rk.cleanarchitecture.MovieApplication;
import com.rk.cleanarchitecture.di.module.ActivityModule;
import com.rk.cleanarchitecture.di.module.ApiModule;
import com.rk.cleanarchitecture.di.module.DBModule;
import com.rk.cleanarchitecture.di.module.FragmentModule;
import com.rk.cleanarchitecture.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ActivityModule.class, ApiModule.class, DBModule.class, ViewModelModule.class, FragmentModule.class})
public interface ApiComponent {

    void inject(MovieApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder dbModule(DBModule dbModule);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        ApiComponent build();
    }
}
