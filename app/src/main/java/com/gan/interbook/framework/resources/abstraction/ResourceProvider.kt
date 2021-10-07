package com.gan.interbook.framework.resources.abstraction

import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes stringId: Int): String

    fun getString(@StringRes stringId: Int, vararg arg: Any): String

    fun getStringArray(@ArrayRes arrayId: Int): Array<String>

    fun getColor(@ColorRes colorId: Int): Int

    fun getDrawable(@DrawableRes drawableId: Int): Drawable?
}