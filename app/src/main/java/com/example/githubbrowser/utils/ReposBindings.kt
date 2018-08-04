package com.example.githubbrowser.utils

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Patrick on 2018/8/4.
 */
class ReposBindings
{
    companion object
    {
        @JvmStatic
        @BindingAdapter("visibleGone")
        fun showHide(view: View, show: Boolean)
        {
            view.visibility = if (show) View.VISIBLE else View.GONE;
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun bindImage(imageView: ImageView, url: String)
        {
            Glide.with(imageView.context)
                    .load(url)
                    .into(imageView);
        }
    }
}