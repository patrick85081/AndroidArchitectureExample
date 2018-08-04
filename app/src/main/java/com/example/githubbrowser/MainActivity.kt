package com.example.githubbrowser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.githubbrowser.services.GithubService
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Patrick on 2018/8/4.
 */
class MainActivity : AppCompatActivity()
{
    lateinit var githubService : GithubService
            @Inject
            set;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);

        if(supportFragmentManager.findFragmentByTag(RepoFragment.TAG) == null)
        {
            val repoFragment = RepoFragment.newInstance();
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, repoFragment, RepoFragment.TAG)
                    .commit();
        }

        githubService.searchRepos("Dagger")
        Timber.d("Hello Dagger !!");

    }
}