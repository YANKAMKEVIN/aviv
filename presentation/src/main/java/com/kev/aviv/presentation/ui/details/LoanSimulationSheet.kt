package com.kev.aviv.presentation.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kev.aviv.common.R
import com.kev.aviv.common.utils.toFormatArea
import com.kev.aviv.common.utils.toFormatPrice

/** Bottom sheet with the estimated monthly payments for borrowing [amount]. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanSimulationSheet(amount: Double, onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        LoanSimulationContent(amount)
    }
}

@Composable
fun LoanSimulationContent(amount: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.loan_simulation_text),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.loan_sheet_amount, amount.toFormatPrice()),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoanCalculator.DURATIONS_YEARS.forEach { years ->
            val monthly = LoanCalculator.monthlyPayment(amount, years) ?: return@forEach
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.loan_sheet_duration, years),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.loan_sheet_monthly, monthly.toFormatPrice()),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(
                R.string.loan_sheet_disclaimer,
                LoanCalculator.INDICATIVE_RATE_PERCENT.toFormatArea()
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
