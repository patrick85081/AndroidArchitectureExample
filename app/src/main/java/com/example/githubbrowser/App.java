package com.example.githubbrowser;

import android.app.Activity;
import android.app.Application;

import com.example.githubbrowser.dagger.AppInjector;
import com.example.githubbrowser.dagger.components.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by Patrick on 2018/8/4.
 */

public class App extends Application implements HasActivityInjector
{
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate()
    {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        AppInjector.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector()
    {
        return dispatchingAndroidInjector;
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {

        }
    }
}
