package com.example.movieapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("srcUrl")
fun ImageView.bindSrcUrl(
    url: String
){
    Glide
        .with(this)
        .load(url)
        .centerInside()
        .into(this)
}