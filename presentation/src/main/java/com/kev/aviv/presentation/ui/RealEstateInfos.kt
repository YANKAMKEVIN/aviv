package com.kev.aviv.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kev.aviv.common.R
import com.kev.aviv.common.utils.toFormatArea
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.presentation.R as PresentationR

/** Surface, rooms and bedrooms with small icons: only the known ones, correctly pluralised. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RealEstateInfos(realEstate: RealEstateInfosDomain, modifier: Modifier = Modifier) {
    val features = buildList {
        if (realEstate.area > 0.0) {
            add(
                PresentationR.drawable.ic_area to
                    stringResource(R.string.list_screen_area_format, realEstate.area.toFormatArea())
            )
        }
        realEstate.rooms?.let {
            add(PresentationR.drawable.ic_rooms to pluralStringResource(R.plurals.list_screen_rooms, it, it))
        }
        realEstate.bedrooms?.let {
            add(PresentationR.drawable.ic_bed to pluralStringResource(R.plurals.list_screen_bedrooms, it, it))
        }
    }
    if (features.isEmpty()) return
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        features.forEach { (icon, label) -> Feature(icon, label) }
    }
}

@Composable
private fun Feature(@DrawableRes icon: Int, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}
