package com.example.githubbrowser.viewmodels.factorys

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Patrick on 2018/8/4.
 */
@Singleton
class GithubViewModelFactory
@Inject
constructor(
        private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
        ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        val creator: Provider<ViewModel>? =
                creators[modelClass] ?: this.creators.entries
                        .firstOrNull { entry -> modelClass.isAssignableFrom(entry.key) }
                        ?.value ?: throw IllegalArgumentException(
                        "Unknown ViewModel class $modelClass");

        try
        {
            return creator!!.get() as T;
        }
        catch (ex: Exception)
        {
            throw RuntimeException(ex);
        }
    }
}