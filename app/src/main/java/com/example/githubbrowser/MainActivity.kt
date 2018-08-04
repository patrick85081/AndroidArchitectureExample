package com.example.githubbrowser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Patrick on 2018/8/4.
 */
class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);

        if(supportFragmentManager.findFragmentByTag(RepoFragment.TAG) == null)
        {
            var repoFragment = RepoFragment.newInstance();
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, repoFragment, RepoFragment.TAG)
                    .commit();
        }

    }
}