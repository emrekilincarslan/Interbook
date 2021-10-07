package com.gan.interbook.presentation.common.ktx

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.gan.interbook.R

fun AppCompatActivity.createErrorDialog(
    message: String
): MaterialDialog =
    createMaterialDialog()
        .title(R.string.text_error)
        .message(text = message)
        .positiveButton(R.string.text_ok) {
            it.dismiss()
        }

fun AppCompatActivity.createChoiceDialog(
    titleId: Int? = null,
    title: String? = null,
    message: String,
    positiveCallback: () -> Unit,
    negativeCallback: (() -> Unit)? = null
): MaterialDialog =
    createMaterialDialog()
        .title(titleId, title)
        .message(text = message)
        .positiveButton(R.string.text_yes) {
            positiveCallback.invoke()
        }
        .negativeButton(R.string.text_no) {
            negativeCallback?.invoke()
        }

fun AppCompatActivity.createInfoDialog(
    message: String,
    positiveCallback: (() -> Unit)? = null
): MaterialDialog =
    createMaterialDialog()
        .title(R.string.text_info)
        .message(text = message)
        .positiveButton(R.string.text_ok) {
            positiveCallback?.invoke()
        }

private fun AppCompatActivity.createMaterialDialog(): MaterialDialog =
    MaterialDialog(this)
        .cancelable(false)
        .lifecycleOwner(this)

fun AppCompatActivity.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    //Find the currently focused view, so we can grab the correct window token from it.
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    val view: View = currentFocus ?: View(this)

    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}