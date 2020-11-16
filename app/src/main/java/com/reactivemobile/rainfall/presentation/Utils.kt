package com.reactivemobile.rainfall.presentation

import android.view.View
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.showHide(show: Boolean) {
    if (show) {
        this.show()
    } else {
        this.gone()
    }
}

fun Date.toHumanReadableDate(): String = SimpleDateFormat("HH:mm dd MMM yyyy", Locale.UK).format(this)