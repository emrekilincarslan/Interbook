package com.gan.interbook.framework.resources.implementation

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import com.gan.interbook.framework.resources.abstraction.ResourceProvider

class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(stringId: Int): String =
        context.getString(stringId)

    override fun getString(stringId: Int, vararg arg: Any): String =
        context.getString(stringId, *arg)

    override fun getStringArray(arrayId: Int): Array<String> =
        context.getStringArray(arrayId)

    override fun getColor(colorId: Int): Int =
        context.getColor(colorId)

    override fun getDrawable(drawableId: Int): Drawable? =
        ContextCompat.getDrawable(context, drawableId)
}