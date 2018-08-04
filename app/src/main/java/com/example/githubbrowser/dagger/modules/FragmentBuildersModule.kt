package com.example.githubbrowser.dagger.modules

import com.example.githubbrowser.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuildersModule
{
    @ContributesAndroidInjector
    fun contributeRepoFragment() : RepoFragment;

}