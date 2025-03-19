package com.kev.aviv.presentation.ui.list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.presentation.R

@Composable
fun RealEstateItem(realEstate: RealEstateInfosDomain) {
Log.d("RealEstateItem", "RealEstateItem: $realEstate")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Image
            AsyncImage(
                model = realEstate.imageUrl,
                contentDescription = "Real Estate Image",
                //contentScale = ContentScale.None,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            /*Image(
                painter = rememberAsyncImagePainter(R.drawable.ic_launcher_foreground),
                contentDescription = "Real Estate Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )*/

            // Infos
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "${realEstate.price} €",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${realEstate.city} - ${realEstate.area} m²", fontSize = 16.sp)
                realEstate.bedrooms?.let {
                    Text(text = "$it chambres - ${realEstate.rooms} pièces", fontSize = 14.sp, color = Color.Gray)
                }
                Text(text = realEstate.propertyType, fontSize = 14.sp, color = Color.Gray)
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
            price = 750000.0,
            professional = "Agence XYZ",
            propertyType = "Appartement",
            offerType = 1,
            bedrooms = 3,
            rooms = 5,
            imageUrl = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg"
        )
    )
}
