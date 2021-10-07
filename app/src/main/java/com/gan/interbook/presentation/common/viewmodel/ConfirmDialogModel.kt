package com.gan.interbook.presentation.common.viewmodel

data class ConfirmDialogModel(
    val title: String,
    val message: String,
    val positiveAction: () -> Unit
)
