package com.kev.aviv.presentation.ui.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kev.aviv.common.R
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.presentation.ui.RealEstateImage

@Composable
fun RealEstateImageCard(realEstate: RealEstateInfosDomain) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )
    {
        RealEstateImage(
            imageUrl = realEstate.imageUrl,
            contentDescription = stringResource(
                R.string.list_screen_real_estate_image_content_desc,
                realEstate.propertyType,
                realEstate.city
            )
        )
    }
}