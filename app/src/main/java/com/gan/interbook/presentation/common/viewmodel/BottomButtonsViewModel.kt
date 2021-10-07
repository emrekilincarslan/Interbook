package com.gan.interbook.presentation.common.viewmodel

class BottomButtonsViewModel(
    val positiveText: String,
    private val positiveCallback: () -> Unit,
    val negativeText: String,
    private val negativeCallback: () -> Unit,
    requireAttention: Boolean = false
) {
    fun negativeCallback() {
        negativeCallback.invoke()
    }

    fun positiveCallback() {
        positiveCallback.invoke()
    }

    val negativeButtonVisible = !requireAttention
    val attentionButtonVisible = requireAttention
}