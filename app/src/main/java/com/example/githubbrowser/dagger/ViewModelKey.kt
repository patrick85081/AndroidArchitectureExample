package com.example.githubbrowser.dagger

import dagger.MapKey
import android.arch.lifecycle.ViewModel
import kotlin.reflect.KClass


/**
 * Created by Patrick on 2018/8/4.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)