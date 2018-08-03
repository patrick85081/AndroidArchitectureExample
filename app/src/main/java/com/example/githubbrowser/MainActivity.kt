package com.example.githubbrowser

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.githubbrowser.databinding.MainActivityBinding

/**
 * Created by Patrick on 2018/8/4.
 */
class MainActivity : AppCompatActivity()
{
    private lateinit var binding: MainActivityBinding;

    private val viewModel: MainViewModel = MainViewModel();

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity);
        binding.viewModel = this.viewModel;

    }
}