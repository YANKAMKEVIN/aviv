package com.kev.aviv.presentation.ui.details

import com.kev.aviv.common.utils.toFormatArea
import kotlin.math.pow

/** Indicative mortgage estimate shown by "Simuler mon crédit" (no down payment, no insurance). */
object LoanCalculator {
    /**
     * Indicative yearly rate, in percent. Kept fixed on purpose (Kevin, 21/11): the sheet labels
     * it as indicative and non-binding rather than letting the user edit it.
     */
    const val INDICATIVE_RATE_PERCENT = 3.5

    /** The indicative rate as shown to the user, French style: "3,5". */
    fun indicativeRateLabel(): String = INDICATIVE_RATE_PERCENT.toFormatArea()

    /** Durations offered by the simulation, in years. */
    val DURATIONS_YEARS = listOf(20, 25)

    /**
     * Constant monthly payment to repay [amount] over [years] at [yearlyRatePercent], or null when
     * the inputs make no sense.
     */
    fun monthlyPayment(
        amount: Double,
        years: Int,
        yearlyRatePercent: Double = INDICATIVE_RATE_PERCENT
    ): Double? {
        if (amount <= 0.0 || years <= 0 || yearlyRatePercent < 0.0) return null
        val months = years * 12
        val monthlyRate = yearlyRatePercent / 100 / 12
        if (monthlyRate == 0.0) return amount / months
        return amount * monthlyRate / (1 - (1 + monthlyRate).pow(-months))
    }
}
