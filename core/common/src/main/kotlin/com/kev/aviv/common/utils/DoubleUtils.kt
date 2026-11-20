package com.kev.aviv.common.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToLong

fun Double.toFormatPrice(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.FRANCE) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(this)
}

/** Surface for display: "250" for 250.0, "72,5" for 72.5 (one decimal, only when useful). */
fun Double.toFormatArea(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.FRANCE) as DecimalFormat
    formatter.applyPattern("#,##0.#")
    return formatter.format(this)
}

/**
 * Price per square meter, rounded to the euro, or null when it cannot be computed
 * (unknown or zero surface, no price).
 */
fun pricePerSquareMeter(price: Double, area: Double): Double? {
    if (price <= 0.0 || area <= 0.0 || price.isNaN() || area.isNaN()) return null
    return (price / area).roundToLong().toDouble()
}
