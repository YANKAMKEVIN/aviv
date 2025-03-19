package com.kev.aviv.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kev.aviv.common.R
import com.kev.aviv.domain.model.RealEstateInfosDomain

@Composable
fun RealEstateInfos(realEstate: RealEstateInfosDomain) {
    Row {
        Text(
            text = stringResource(R.string.list_screen_area_format, realEstate.area),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        realEstate.rooms?.let {
            Text(stringResource(R.string.list_screen_separator))
            Text(
                text = stringResource(
                    R.string.list_screen_rooms_format,
                    it
                ),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        realEstate.bedrooms?.let {
            Text(stringResource(R.string.list_screen_separator))
            Text(
                text = stringResource(R.string.list_screen_bedrooms_format, it),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}