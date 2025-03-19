package com.kev.aviv.presentation.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kev.aviv.common.R
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.presentation.ui.RealEstateImage
import com.kev.aviv.presentation.ui.RealEstateInfos

@Composable
fun RealEstateDetailsItem(realEstate: RealEstateInfosDomain) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        RealEstateImage(imageUrl = realEstate.imageUrl, imageHeight = 400.dp)
        Column(modifier = Modifier.padding(12.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (realEstate.offerType) {
                    0, 1 -> stringResource(R.string.list_screen_offer_type_build)
                    else -> stringResource(R.string.list_screen_offer_type_house)
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(20.dp))
            RealEstateInfos(realEstate)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = realEstate.city,
                fontSize = 16.sp,
                color = Color(0xFFE91E63),
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(20.dp))
            PriceDetails(realEstate.price)
            Spacer(modifier = Modifier.height(8.dp))
            LoanSimulation(onClick = {})
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.details_screen_proposition_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1.0f))

            ContactActionButtons()
        }

    }
}