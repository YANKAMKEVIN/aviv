package com.kev.aviv.presentation.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kev.aviv.common.R
import com.kev.aviv.domain.model.RealEstateInfosDomain
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RealEstateItem(realEstate: RealEstateInfosDomain) {
    val formattedPrice = formatPrice(realEstate.price)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            )
            {
                AsyncImage(
                    model = realEstate.imageUrl?:R.drawable.empty_state,
                    contentDescription = stringResource(R.string.list_screen_real_estate_image_content_desc),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.list_screen_price_format, formattedPrice),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Text(
                        text = stringResource(R.string.list_screen_property_type_label),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(
                            0xFF4CAF50
                        )
                    )
                    Text(stringResource(R.string.list_screen_separator))
                    Text(
                        text = realEstate.propertyType,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
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

fun formatPrice(price: Double): String {
    val formatter = NumberFormat.getNumberInstance(Locale.FRANCE) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(price)
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
