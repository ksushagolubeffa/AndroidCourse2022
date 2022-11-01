package com.example.homework3

import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

typealias Click = () -> Unit

fun Fragment.showDialog(
    positiveAction: Click? = null,
    negativeAction: Click? = null,
    neutralAction: Click? = null,
) {
    val view =
           LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog, null, false)
    AlertDialog.Builder(requireContext())
        .setView(view)
        .setPositiveButton("PLUS") { dialog, _ ->
            positiveAction?.invoke()
            dialog.dismiss()
        }
        .setNegativeButton("CLOSE") { dialog, _ ->
            negativeAction?.invoke()
            dialog.dismiss()
        }
        .setNeutralButton("MINUS") { dialog, _ ->
            neutralAction?.invoke()
            dialog.dismiss()
        }
        .show()
}