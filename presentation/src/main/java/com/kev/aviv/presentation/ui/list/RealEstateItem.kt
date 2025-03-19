package com.kev.aviv.presentation.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kev.aviv.common.R
import com.kev.aviv.common.utils.toFormatPrice
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.presentation.ui.RealEstateInfos

@Composable
fun RealEstateItem(realEstate: RealEstateInfosDomain, onClick: (id: String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {

            RealEstateImageCard(realEstate, { onClick(realEstate.id.toString()) })

            Column(modifier = Modifier.padding(12.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        R.string.list_screen_price_format,
                        realEstate.price.toFormatPrice()
                    ),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(12.dp))
                RealEstateType(realEstate.propertyType)
                Spacer(modifier = Modifier.height(12.dp))
                RealEstateInfos(realEstate)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = realEstate.city,
                    fontSize = 16.sp,
                    color = Color(0xFFE91E63),
                    fontStyle = FontStyle.Italic
                )
                Text(text = realEstate.professional, fontSize = 12.sp, color = Color.LightGray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RealEstateItemPreview() {
    RealEstateItem(
        realEstate = RealEstateInfosDomain(
            id = 1,
            city = "Paris",
            area = 100.0,
            price = 1750000.0,
            professional = "Agence XYZ",
            propertyType = "Appartement",
            offerType = 1,
            bedrooms = 3,
            rooms = 5,
            imageUrl = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg"
        )
    )
}
