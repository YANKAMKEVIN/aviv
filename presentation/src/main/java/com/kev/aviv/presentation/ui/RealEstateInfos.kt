package com.kev.aviv.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kev.aviv.common.R
import com.kev.aviv.common.utils.toFormatArea
import com.kev.aviv.domain.model.RealEstateInfosDomain

/** "250 m² • 8 pièces • 4 chambres": only the known characteristics, correctly pluralised. */
@Composable
fun RealEstateInfos(realEstate: RealEstateInfosDomain) {
    val parts = buildList {
        if (realEstate.area > 0.0) {
            add(stringResource(R.string.list_screen_area_format, realEstate.area.toFormatArea()))
        }
        realEstate.rooms?.let { add(pluralStringResource(R.plurals.list_screen_rooms, it, it)) }
        realEstate.bedrooms?.let { add(pluralStringResource(R.plurals.list_screen_bedrooms, it, it)) }
    }
    if (parts.isEmpty()) return
    Text(
        text = parts.joinToString(stringResource(R.string.list_screen_separator)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}
