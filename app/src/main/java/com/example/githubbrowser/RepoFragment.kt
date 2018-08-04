package com.example.githubbrowser

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubbrowser.databinding.RepoFragmentBinding
import com.example.githubbrowser.models.DataModel
import com.example.githubbrowser.services.models.Repo
import com.example.githubbrowser.viewmodels.RepoViewModel
import com.example.githubbrowser.viewmodels.factorys.GithubViewModelFactory
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


/**
 * Created by Patrick on 2018/8/4.
 */
class RepoFragment : Fragment()
{

    private val viewModel: RepoViewModel by lazy {
        val factory = GithubViewModelFactory(DataModel());
        ViewModelProviders.of(this, factory).get(RepoViewModel::class.java) };

    private val repoAdapter = RepoAdapter(ArrayList<Repo>());
    private lateinit var binding: RepoFragmentBinding;

    companion object
    {
        val TAG = "RepoFragment";
        @JvmStatic
        fun newInstance() : RepoFragment = RepoFragment();
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate<RepoFragmentBinding>(inflater, R.layout.repo_fragment, container, false);

        binding.btnSearch.setOnClickListener {
            doSearch();
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.adapter = repoAdapter;

        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel;
        viewModel.repos.observe(this, Observer {response ->

            if(response!!.isSuccessful)
                repoAdapter.swapItems(response!!.body!!.items);
            else
            {
                Toast.makeText(context, response.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private fun doSearch()
    {
        val query = binding.edtQuery.text.toString();
        if(TextUtils.isEmpty(query))
        {
            repoAdapter.clearItems();
            return;
        }

        viewModel.searchRepo(query);
        dismissKeyboard();
    }

    private fun dismissKeyboard()
    {
        val view = activity!!.currentFocus
        if (view != null)
        {
            val imm = activity!!.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}