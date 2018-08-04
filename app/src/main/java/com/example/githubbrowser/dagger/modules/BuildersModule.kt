package com.example.githubbrowser.dagger.modules

import com.example.githubbrowser.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule()
{
    @ContributesAndroidInjector
    abstract fun constributeMainActivity() : MainActivity;
}