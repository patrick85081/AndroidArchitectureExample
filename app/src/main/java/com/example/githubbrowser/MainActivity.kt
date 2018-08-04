package com.example.githubbrowser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.githubbrowser.services.GithubService
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Patrick on 2018/8/4.
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector
{
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector;
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
        @Inject
        set;

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