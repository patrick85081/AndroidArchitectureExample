package com.example.githubbrowser

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.githubbrowser.databinding.RepoItemBinding
import com.example.githubbrowser.services.models.Repo

/**
 * Created by Patrick on 2018/8/4.
 */
class RepoAdapter(private var items: MutableList<Repo>) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context);
        val binding : RepoItemBinding = RepoItemBinding.inflate(layoutInflater, parent, false);

        return RepoViewHolder(binding);
    }

    override fun getItemCount(): Int = items.size;

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int)
    {
        val repo = items[position];
        holder.bind(repo);
    }

    fun swapItems(newItems: List<Repo>)
    {
        val result = DiffUtil.calculateDiff(RepoDiffCallback(this.items, newItems));
        this.items.clear();
        this.items.addAll(newItems);
        result.dispatchUpdatesTo(this);
    }

    fun clearItems()
    {
        val size = this.items.size;
        this.items.clear();
        notifyItemRangeRemoved(0, size);
    }



    class RepoViewHolder(private val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(repo: Repo)
        {
            binding.repo = repo;
            // 強制馬上綁定
            binding.executePendingBindings();
        }
    }

    class RepoDiffCallback(private var oldList: List<Repo>, private var newList : List<Repo>) : DiffUtil.Callback()
    {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        {
            return oldList[oldItemPosition].id == newList[newItemPosition].id;
        }

        override fun getOldListSize(): Int = oldList.size;
        override fun getNewListSize(): Int = newList.size;

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        {
            return oldList[oldItemPosition] == newList[newItemPosition];
        }

    }
}