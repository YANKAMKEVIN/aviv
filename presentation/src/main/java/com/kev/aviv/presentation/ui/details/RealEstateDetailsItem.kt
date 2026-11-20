package com.kev.aviv.presentation.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
fun RealEstateDetailsItem(
    realEstate: RealEstateInfosDomain,
    onBack: (() -> Unit)? = null
) {
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            ContactActionButtons(
                onCall = { if (!context.openDialer()) showMessage(noDialer) },
                onMessage = { context.shareText(shareText, shareTitle) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                RealEstateImage(imageUrl = realEstate.imageUrl)
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.details_back)
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.details_title, realEstate.propertyType, realEstate.city),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(12.dp))
                RealEstateInfos(realEstate)
                Spacer(modifier = Modifier.height(20.dp))
                PriceDetails(
                    realEstatePrice = realEstate.price,
                    squareMeterPrice = pricePerSquareMeter(realEstate.price, realEstate.area)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LoanSimulation(onClick = { showLoanSimulation = true })
                OutlinedButton(
                    onClick = { showMessage(comingSoon) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.details_screen_proposition_button),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                AgencyRow(realEstate.professional)
                Spacer(modifier = Modifier.height(16.dp))
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
