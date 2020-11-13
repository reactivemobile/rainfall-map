package com.reactivemobile.rainfall.presentation

import android.view.View
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun View.gone() {
    this.visibility = View.GONE
}

fun Date.toHumanReadableDate(): String = SimpleDateFormat("HH:mm dd MMM yyyy", Locale.UK).format(this)