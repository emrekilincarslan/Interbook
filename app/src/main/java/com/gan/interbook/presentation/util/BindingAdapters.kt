package com.gan.interbook.presentation.util

import android.graphics.drawable.Drawable
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.MainScope

@BindingAdapter("visibility")
fun setViewVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:onClick")
fun setClickListener(
    view: View,
    onClickListener: View.OnClickListener
) {
    val scope = ViewTreeLifecycleOwner.get(view)?.lifecycleScope ?: MainScope()

    val clickWithDebounce: (view: View) -> Unit =
        debounce(scope = scope) {
            onClickListener.onClick(it)
        }

    view.setOnClickListener(clickWithDebounce)
}

@BindingAdapter("query")
fun setQuery(searchView: SearchView, queryText:String) {
    searchView.setQuery(queryText, false);
}

@BindingAdapter("queryTextListener")
fun setOnQueryTextListener(searchView: SearchView, listener: SearchView.OnQueryTextListener ) {
    searchView.setOnQueryTextListener(listener);
}

@BindingAdapter("app:bookImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}