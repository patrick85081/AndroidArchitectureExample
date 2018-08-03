package com.example.githubbrowser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

/**
 * Created by Patrick on 2018/8/4.
 */
class MainActivity : AppCompatActivity()
{
    private val btnRefresh: Button by lazy { findViewById<Button>(R.id.btnRefresh) }
    private val viewModel: MainViewModel = MainViewModel();

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity);

        btnRefresh.setOnClickListener {
            viewModel.refresh();
        }
    }
}