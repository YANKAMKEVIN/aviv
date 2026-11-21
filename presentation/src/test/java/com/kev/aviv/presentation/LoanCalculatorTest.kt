package com.kev.aviv.presentation

import com.kev.aviv.presentation.ui.details.LoanCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LoanCalculatorTest {

    @Test
    fun `monthly payment follows the constant annuity formula`() {
        // 200 000 € over 20 years at 3.5 %: 1 159.92 € per month
        assertEquals(1159.92, LoanCalculator.monthlyPayment(200_000.0, 20, 3.5)!!, 0.01)
    }

    @Test
    fun `a longer loan lowers the monthly payment`() {
        val twenty = LoanCalculator.monthlyPayment(300_000.0, 20)!!
        val twentyFive = LoanCalculator.monthlyPayment(300_000.0, 25)!!
        assert(twentyFive < twenty)
    }

    @Test
    fun `a zero rate splits the amount evenly`() {
        assertEquals(1000.0, LoanCalculator.monthlyPayment(240_000.0, 20, 0.0)!!, 0.0001)
    }

    @Test
    fun `no estimate without an amount or a duration`() {
        assertNull(LoanCalculator.monthlyPayment(0.0, 20))
        assertNull(LoanCalculator.monthlyPayment(200_000.0, 0))
    }

    @Test
    fun `the simulation keeps the indicative rate of 3,5 percent`() {
        assertEquals(3.5, LoanCalculator.INDICATIVE_RATE_PERCENT, 0.0)
        assertEquals(
            LoanCalculator.monthlyPayment(200_000.0, 20, 3.5),
            LoanCalculator.monthlyPayment(200_000.0, 20)
        )
    }

    @Test
    fun `the indicative rate is shown with a French decimal comma`() {
        assertEquals("3,5", LoanCalculator.indicativeRateLabel())
    }
}
