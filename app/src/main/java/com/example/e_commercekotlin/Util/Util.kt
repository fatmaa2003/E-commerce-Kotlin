package com.example.e_commercekotlin.Util

import android.content.res.Resources

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

