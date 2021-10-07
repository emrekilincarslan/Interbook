package com.gan.interbook.presentation.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerPaginationScrollListener(private val thresholdReachedCallback: () -> Unit) :
    RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        val lastVisibleItem =
            (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        val itemCount = recyclerView.adapter?.itemCount ?: 0

        if (lastVisibleItem + PAGINATION_LOAD_THRESHOLD >= itemCount) {
            thresholdReachedCallback.invoke()
        }
    }

    companion object {
        private const val PAGINATION_LOAD_THRESHOLD = 5
    }
}