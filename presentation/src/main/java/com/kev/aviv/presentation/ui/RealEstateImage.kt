package com.kev.aviv.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RealEstateImage(imageUrl: String?, imageHeight: Dp = 200.dp) {
    AsyncImage(
        model = imageUrl ?: com.kev.aviv.common.R.drawable.empty_state,
        contentDescription = stringResource(com.kev.aviv.common.R.string.list_screen_real_estate_image_content_desc),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
    )
}
