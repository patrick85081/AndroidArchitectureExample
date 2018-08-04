package com.example.githubbrowser.dagger

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.example.githubbrowser.App
import com.example.githubbrowser.dagger.components.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * Created by Patrick on 2018/8/4.
 */
class AppInjector
{
    companion object
    {
        @JvmStatic
        fun init(app: App)
        {
            DaggerAppComponent.builder()
                    .application(app)
                    .build()
                    .inject(app);

            app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks
            {
                override fun onActivityPaused(activity: Activity?)
                {
                }

                override fun onActivityResumed(activity: Activity?)
                {
                }

                override fun onActivityStarted(activity: Activity?)
                {
                }

                override fun onActivityDestroyed(activity: Activity?)
                {
                }

                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?)
                {
                }

                override fun onActivityStopped(activity: Activity?)
                {
                }

                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?)
                {
                    handleActivity(activity!!);
                }
            });
        }

        @JvmStatic
        private fun handleActivity(activity: Activity)
        {
            if(activity is Injectable || activity is HasSupportFragmentInjector)
                AndroidInjection.inject(activity);
            if(activity is FragmentActivity)
            {
                activity.supportFragmentManager
                        .registerFragmentLifecycleCallbacks(object :
                                FragmentManager.FragmentLifecycleCallbacks()
                        {
                            override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?,
                                                            context: Context?)
                            {
                                if(f is Injectable)
                                    AndroidSupportInjection.inject(f);
                            }
                        }, true);
            }
        }
    }
}