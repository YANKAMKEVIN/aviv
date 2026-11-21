package com.kev.aviv.presentation.ui.details

import android.content.Context
import android.content.Intent

/** Lets the user send the listing summary through any messaging app. */
fun Context.shareText(text: String, chooserTitle: String) {
    val send = Intent(Intent.ACTION_SEND)
        .setType("text/plain")
        .putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(send, chooserTitle))
}
