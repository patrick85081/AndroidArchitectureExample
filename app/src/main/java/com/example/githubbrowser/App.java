package com.example.githubbrowser;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Patrick on 2018/8/4.
 */

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {

        }
    }
}
