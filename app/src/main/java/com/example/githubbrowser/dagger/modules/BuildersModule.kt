package com.example.githubbrowser.dagger.modules

import com.example.githubbrowser.MainActivity
import com.example.githubbrowser.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule
{
    @ContributesAndroidInjector
    fun constributeMainActivity() : MainActivity;
}

@Module
interface FragmentBuildersModule
{
    @ContributesAndroidInjector
    fun contributeRepoFragment() : RepoFragment;

}