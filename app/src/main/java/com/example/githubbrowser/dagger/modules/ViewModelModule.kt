package com.example.githubbrowser.dagger.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.githubbrowser.dagger.ViewModelKey
import com.example.githubbrowser.viewmodels.RepoViewModel
import com.example.githubbrowser.viewmodels.UserViewModel
import com.example.githubbrowser.viewmodels.factorys.GithubViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

/**
 * Created by Patrick on 2018/8/4.
 */
@Module
abstract class ViewModelModule
{
    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel) : ViewModel;

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel) : ViewModel;

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory) : ViewModelProvider.Factory;
}