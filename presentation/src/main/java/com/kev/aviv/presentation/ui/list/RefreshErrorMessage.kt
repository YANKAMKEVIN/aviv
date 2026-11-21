package com.kev.aviv.presentation.ui.list

import androidx.annotation.StringRes
import com.kev.aviv.common.R
import com.kev.aviv.common.loader.ErrorType

/** Snackbar text shown when a pull-to-refresh fails while listings stay displayed. */
@StringRes
fun ErrorType.refreshErrorMessage(): Int = when (this) {
    ErrorType.NETWORK -> R.string.list_screen_refresh_network_error
    ErrorType.SERVER -> R.string.list_screen_refresh_server_error
    ErrorType.UNKNOWN -> R.string.list_screen_refresh_error
}
