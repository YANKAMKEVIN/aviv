package com.kev.aviv.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.kev.aviv.presentation.ui.list.RealEstateListRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AvivApp() {
    Scaffold(
        topBar = {

        },
        content = {
            RealEstateListRoute()
        }
    )
}