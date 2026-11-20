package com.kev.aviv.presentation.ui.details

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent

/** Opens the phone dialer; returns false when the device has none (e.g. Wi-Fi tablets). */
fun Context.openDialer(): Boolean = try {
    startActivity(Intent(Intent.ACTION_DIAL))
    true
} catch (e: ActivityNotFoundException) {
    false
}

/** Lets the user send the listing summary through any messaging app. */
fun Context.shareText(text: String, chooserTitle: String) {
    val send = Intent(Intent.ACTION_SEND)
        .setType("text/plain")
        .putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(send, chooserTitle))
}
