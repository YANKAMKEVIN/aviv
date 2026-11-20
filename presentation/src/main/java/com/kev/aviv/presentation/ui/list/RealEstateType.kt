package com.kev.aviv.presentation.ui.list

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kev.aviv.common.R

@Composable
fun RealEstateType(propertyType: String) {
    Row {
        Text(
            text = stringResource(R.string.list_screen_property_type_label),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(stringResource(R.string.list_screen_separator))
        Text(
            text = propertyType,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

    }
}