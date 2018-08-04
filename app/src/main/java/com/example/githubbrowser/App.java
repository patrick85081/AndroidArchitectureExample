package com.example.githubbrowser;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.githubbrowser.dagger.AppInjector;
import com.example.githubbrowser.dagger.components.DaggerAppComponent;

import java.lang.reflect.Method;

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
        showDebugDBAddressLogToast(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector()
    {
        return dispatchingAndroidInjector;
    }

    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
            } catch (Exception ignore) {

            }
        }
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {

        }
    }
}
