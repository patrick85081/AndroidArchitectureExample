package com.example.githubbrowser.dagger.components

import com.example.githubbrowser.App
import com.example.githubbrowser.dagger.modules.ActivityBuildersModule
import com.example.githubbrowser.dagger.modules.AppModule
import com.example.githubbrowser.dagger.modules.FragmentBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Patrick on 2018/8/4.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuildersModule::class,
    FragmentBuildersModule::class])
abstract class AppComponent
{
    @Component.Builder
    interface Builder
    {
        @BindsInstance
        fun application(application: App) : Builder;
        fun build() : AppComponent;
    }

    abstract fun inject(app: App);
}