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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kev.aviv.common.R
import com.kev.aviv.common.utils.pricePerSquareMeter
import com.kev.aviv.common.utils.toFormatPrice
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.presentation.ui.RealEstateImage
import com.kev.aviv.presentation.ui.RealEstateInfos
import kotlinx.coroutines.launch

@Composable
fun RealEstateDetailsItem(realEstate: RealEstateInfosDomain) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showLoanSimulation by rememberSaveable { mutableStateOf(false) }

    val comingSoon = stringResource(R.string.details_coming_soon)
    val noDialer = stringResource(R.string.details_no_dialer)
    val shareTitle = stringResource(R.string.details_share_title)
    val shareText = stringResource(
        R.string.details_share_text,
        realEstate.propertyType,
        realEstate.city,
        realEstate.price.toFormatPrice()
    )
    fun showMessage(message: String) {
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(20.dp))
                PriceDetails(
                    realEstatePrice = realEstate.price,
                    squareMeterPrice = pricePerSquareMeter(realEstate.price, realEstate.area)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LoanSimulation(onClick = { showLoanSimulation = true })
                Button(
                    onClick = { showMessage(comingSoon) },
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

                ContactActionButtons(
                    onCall = { if (!context.openDialer()) showMessage(noDialer) },
                    onMessage = { context.shareText(shareText, shareTitle) }
                )
            }
        }
    }

    if (showLoanSimulation) {
        LoanSimulationSheet(
            amount = realEstate.price,
            onDismiss = { showLoanSimulation = false }
        )
    }
}
