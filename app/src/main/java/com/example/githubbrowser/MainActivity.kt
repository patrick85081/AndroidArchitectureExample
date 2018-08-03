package com.example.githubbrowser

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.githubbrowser.databinding.MainActivityBinding
import com.example.githubbrowser.model.DataModel
import com.example.githubbrowser.viewmodel.MainViewModel
import com.example.githubbrowser.viewmodel.factory.MainVIewModelFactory

/**
 * Created by Patrick on 2018/8/4.
 */
class MainActivity : AppCompatActivity()
{
    private lateinit var binding: MainActivityBinding;

    private val viewModel: MainViewModel by lazy {
        val factory = MainVIewModelFactory(DataModel());
        ViewModelProviders.of(this, factory).get(MainViewModel::class.java) };

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity);
        binding.viewModel = this.viewModel;

        viewModel.dataTitle.observe(this, Observer<String> {data ->
            binding.txtHelloWord.text = data;
        });

        viewModel.toastText.observe(this, Observer<String> {
            Toast.makeText(this@MainActivity, "下載完成", Toast.LENGTH_SHORT).show();
        })

    }
}