package com.example.githubbrowser.dagger.modules

import com.example.githubbrowser.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule
{
    @ContributesAndroidInjector//(modules = [FragmentBuildersModule::class])
    fun constributeMainActivity() : MainActivity;
}