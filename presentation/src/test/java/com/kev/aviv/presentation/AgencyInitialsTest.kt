package com.kev.aviv.presentation

import com.kev.aviv.presentation.ui.details.agencyInitials
import org.junit.Assert.assertEquals
import org.junit.Test

class AgencyInitialsTest {

    @Test
    fun `initials come from the first two words`() {
        assertEquals("GE", agencyInitials("GSL EXPLORE"))
        assertEquals("AX", agencyInitials("Agence XYZ Immobilier"))
    }

    @Test
    fun `initials are upper case`() {
        assertEquals("AR", agencyInitials("agence rhône"))
    }

    @Test
    fun `a single word gives a single initial`() {
        assertEquals("S", agencyInitials("SEXTANT"))
    }

    @Test
    fun `separators and extra spaces are ignored`() {
        assertEquals("AM", agencyInitials("  Adresse - Maisons  "))
        assertEquals("", agencyInitials("   "))
    }
}
