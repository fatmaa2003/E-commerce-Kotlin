package com.example.e_commercekotlin.Util

import android.content.res.Resources
import android.view.View
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.CustomToolbarBinding

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

 fun CustomToolbarBinding.handleToolBarState(
    toolBarTitle : String = "",
    searchVisibility: Boolean = true,
    rightIconVisibility: Boolean = false,
    leftIconVisibility: Boolean = true,
    rightIconImage: Int = R.drawable.disk,
    leftIconImage: Int = R.drawable.back
) {
    this.toolbarTitle.text = toolBarTitle
    this.searchIcon.visibility = if (searchVisibility) View.VISIBLE else View.GONE
    this.placeHolderIcon.visibility = if (rightIconVisibility) View.VISIBLE else View.GONE
    this.leftIcon.visibility = if (leftIconVisibility) View.VISIBLE else View.GONE
    this.placeHolderIcon.setImageResource(rightIconImage)
    this.leftIcon.setImageResource(leftIconImage)
}

