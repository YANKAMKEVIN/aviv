package com.kev.aviv.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kev.aviv.presentation.ui.HomeScreen

@Composable
fun AvivApp() {
    // Each pane draws its own header: the list has its app bar, the details their photo, which
    // goes under the status bar. Only side insets (landscape cutout or navigation bar) apply here.
    HomeScreen(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
    )
}
