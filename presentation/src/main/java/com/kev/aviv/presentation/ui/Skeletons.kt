package com.kev.aviv.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.kev.aviv.common.R
import com.kev.aviv.common.loader.SkeletonBlock
import com.kev.aviv.common.loader.rememberShimmerBrush

/** Three ghost listing cards shown while the list loads. */
@Composable
fun RealEstateListSkeleton() {
    val brush = rememberShimmerBrush()
    val description = stringResource(R.string.loading_content_desc)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = description }
    ) {
        repeat(3) {
            Column(modifier = Modifier.padding(8.dp)) {
                SkeletonBlock(
                    brush = brush,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(REAL_ESTATE_IMAGE_RATIO),
                    shape = RoundedCornerShape(12.dp)
                )
                Column(modifier = Modifier.padding(12.dp)) {
                    SkeletonLines(brush)
                }
            }
        }
    }
}

/** Ghost of the detail page shown while a listing loads. */
@Composable
fun RealEstateDetailsSkeleton() {
    val brush = rememberShimmerBrush()
    val description = stringResource(R.string.loading_content_desc)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = description }
    ) {
        SkeletonBlock(
            brush = brush,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(REAL_ESTATE_IMAGE_RATIO),
            shape = RoundedCornerShape(0.dp)
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonLines(brush)
            Spacer(modifier = Modifier.height(20.dp))
            SkeletonBlock(brush, Modifier.fillMaxWidth(0.4f).height(24.dp))
            Spacer(modifier = Modifier.height(16.dp))
            SkeletonBlock(brush, Modifier.fillMaxWidth().height(48.dp), RoundedCornerShape(24.dp))
        }
    }
}

@Composable
private fun SkeletonLines(brush: Brush) {
    SkeletonBlock(brush, Modifier.fillMaxWidth(0.45f).height(22.dp))
    Spacer(modifier = Modifier.height(12.dp))
    SkeletonBlock(brush, Modifier.fillMaxWidth(0.7f).height(16.dp))
    Spacer(modifier = Modifier.height(12.dp))
    SkeletonBlock(brush, Modifier.fillMaxWidth(0.35f).height(16.dp))
}
