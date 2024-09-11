package com.example.e_commercekotlin.Util

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.CustomToolbarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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

// function to show or hide bottom nav bar
fun Activity.setBottomNavVisibility(visible: Boolean) {
    val bottomNavView = this.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    bottomNavView?.apply {
        visibility = if (visible) View.VISIBLE else View.GONE
    }
}

// Extension function to show the ProgressBar
fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}

// Extension function to hide the ProgressBar
fun ProgressBar.hide() {
    this.visibility = View.GONE
}
// Extension function to Show a toast
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show() 
}

