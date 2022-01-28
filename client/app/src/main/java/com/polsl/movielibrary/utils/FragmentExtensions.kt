package com.polsl.movielibrary.utils

import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.polsl.movielibrary.R

fun Fragment.setOnBackPressed(onBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    )
}

inline fun Fragment.showDialog(
    title: String? = null,
    message: String? = null,
    positiveButton: (Pair<String, () -> Unit>)? = null,
    negativeButton: (Pair<String, () -> Unit>)? = null,
    crossinline onCancel: (() -> Unit) = {},
    cancelable: Boolean = true,
    @ColorRes buttonColor: Int = R.color.black
): AlertDialog {
    with(AlertDialog.Builder(requireActivity())) {
        setTitle(title)
        setMessage(message)
        if (positiveButton != null) setPositiveButton(positiveButton.first) { _, _ ->
            positiveButton.second.invoke()
        }
        if (negativeButton != null) setNegativeButton(negativeButton.first) { _, _ ->
            negativeButton.second.invoke()
        }

        setOnCancelListener { onCancel() }
        setCancelable(cancelable)
        val dialog = show()

        dialog.setOnShowListener { dialog.dismiss() }
        dialog.window?.run {
//            findViewById<TextView>(
//                context.resources.getIdentifier(
//                    "alertTitle",
//                    "id",
//                    "android"
//                )
//            )?.setTextAppearance()
            val firstButton: Button = findViewById(android.R.id.button1)
            val secondButton: Button = findViewById(android.R.id.button2)
            val body: TextView = findViewById(android.R.id.message)

//            body.setTextAppearance()
//            firstButton.setTextAppearance()
//            secondButton.setTextAppearance()
            if (buttonColor == ResourcesCompat.ID_NULL) return  dialog
            val color = context.getColor(buttonColor)
            firstButton.setTextColor(color)
            secondButton.setTextColor(color)
        }
        return dialog
    }
}