package com.kev.aviv.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kev.aviv.presentation.ui.HomeScreen

@Composable
fun AvivApp() {
    Scaffold(
        topBar = {

        },
        content = {
            HomeScreen(modifier = Modifier.padding(it))
        }
    )
}