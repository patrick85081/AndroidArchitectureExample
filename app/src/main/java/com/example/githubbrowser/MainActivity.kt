package com.example.githubbrowser

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.githubbrowser.dagger.Injectable
import com.example.githubbrowser.services.GithubService
import com.example.githubbrowser.viewmodels.UserViewModel
import com.example.githubbrowser.viewmodels.factorys.GithubViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Patrick on 2018/8/4.
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, Injectable
{
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector;
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
        @Inject
        set;

    lateinit var githubService: GithubService
        @Inject
        set;

    lateinit var githubViewModelFactory: GithubViewModelFactory
        @Inject
        set;

    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, githubViewModelFactory).get(UserViewModel::class.java)
    };

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);

        if (supportFragmentManager.findFragmentByTag(RepoFragment.TAG) == null)
        {
            val repoFragment = RepoFragment.newInstance();
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, repoFragment, RepoFragment.TAG)
                    .commit();
        }

        githubService.searchRepos("Dagger")
                .observe(this, Observer { Timber.d("Hello Dagger !!") });

        viewModel.test({ Timber.d("Hello UserViewModel !!")});

    }
}