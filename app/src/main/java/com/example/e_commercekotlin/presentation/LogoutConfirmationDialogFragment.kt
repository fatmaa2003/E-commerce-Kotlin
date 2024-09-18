package com.example.e_commercekotlin.presentation

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class LogoutConfirmationDialogFragment(private val onConfirm: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                onConfirm.invoke()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    companion object {
        fun newInstance(onConfirm: () -> Unit): LogoutConfirmationDialogFragment {
            return LogoutConfirmationDialogFragment(onConfirm)
        }
    }
}
