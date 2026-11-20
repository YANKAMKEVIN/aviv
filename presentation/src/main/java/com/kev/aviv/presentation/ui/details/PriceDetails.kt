package com.kev.aviv.presentation.ui.details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kev.aviv.common.R
import com.kev.aviv.common.utils.toFormatPrice

/** Price of the listing, followed by its price per m² when the surface is known. */
@Composable
fun PriceDetails(realEstatePrice: Double, squareMeterPrice: Double? = null) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = stringResource(
                R.string.list_screen_price_format,
                realEstatePrice.toFormatPrice()
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        if (squareMeterPrice != null) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(
                    R.string.price_per_square_meter_text,
                    squareMeterPrice.toFormatPrice()
                ),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}