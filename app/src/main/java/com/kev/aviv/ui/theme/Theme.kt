package com.kev.aviv.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = BrandRed,
    onPrimary = RedLightOnPrimary,
    primaryContainer = RedLightPrimaryContainer,
    onPrimaryContainer = RedLightOnPrimaryContainer,
    inversePrimary = RedLightInversePrimary,
    secondary = NeutralLightSecondary,
    onSecondary = RedLightOnPrimary,
    secondaryContainer = NeutralLightSecondaryContainer,
    onSecondaryContainer = NeutralLightOnSecondaryContainer,
    tertiary = NeutralLightTertiary,
    onTertiary = RedLightOnPrimary,
    tertiaryContainer = NeutralLightTertiaryContainer,
    onTertiaryContainer = NeutralLightOnTertiaryContainer,
    background = NeutralLightBackground,
    onBackground = NeutralLightOnBackground,
    surface = NeutralLightBackground,
    onSurface = NeutralLightOnBackground,
    surfaceVariant = NeutralLightSurfaceVariant,
    onSurfaceVariant = NeutralLightOnSurfaceVariant,
    surfaceTint = BrandRed,
    inverseSurface = NeutralLightInverseSurface,
    inverseOnSurface = NeutralLightInverseOnSurface,
    outline = NeutralLightOutline,
    outlineVariant = NeutralLightOutlineVariant,
    surfaceBright = NeutralLightBackground,
    surfaceDim = NeutralLightSurfaceContainerHighest,
    surfaceContainerLowest = NeutralLightSurfaceContainerLowest,
    surfaceContainerLow = NeutralLightSurfaceContainerLow,
    surfaceContainer = NeutralLightSurfaceContainer,
    surfaceContainerHigh = NeutralLightSurfaceContainerHigh,
    surfaceContainerHighest = NeutralLightSurfaceContainerHighest,
)

private val DarkColorScheme = darkColorScheme(
    primary = RedDarkPrimary,
    onPrimary = RedDarkOnPrimary,
    primaryContainer = RedDarkPrimaryContainer,
    onPrimaryContainer = RedDarkOnPrimaryContainer,
    inversePrimary = BrandRed,
    secondary = NeutralDarkSecondary,
    onSecondary = NeutralDarkInverseOnSurface,
    secondaryContainer = NeutralDarkSecondaryContainer,
    onSecondaryContainer = NeutralDarkOnSecondaryContainer,
    tertiary = NeutralDarkTertiary,
    onTertiary = NeutralDarkInverseOnSurface,
    tertiaryContainer = NeutralDarkTertiaryContainer,
    onTertiaryContainer = NeutralDarkOnTertiaryContainer,
    background = NeutralDarkBackground,
    onBackground = NeutralDarkOnBackground,
    surface = NeutralDarkBackground,
    onSurface = NeutralDarkOnBackground,
    surfaceVariant = NeutralDarkSurfaceVariant,
    onSurfaceVariant = NeutralDarkOnSurfaceVariant,
    surfaceTint = RedDarkPrimary,
    inverseSurface = NeutralDarkInverseSurface,
    inverseOnSurface = NeutralDarkInverseOnSurface,
    outline = NeutralDarkOutline,
    outlineVariant = NeutralDarkOutlineVariant,
    surfaceBright = NeutralDarkSurfaceContainerHighest,
    surfaceDim = NeutralDarkBackground,
    surfaceContainerLowest = NeutralDarkSurfaceContainerLowest,
    surfaceContainerLow = NeutralDarkSurfaceContainerLow,
    surfaceContainer = NeutralDarkSurfaceContainer,
    surfaceContainerHigh = NeutralDarkSurfaceContainerHigh,
    surfaceContainerHighest = NeutralDarkSurfaceContainerHighest,
)

@Composable
fun AvivTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color (Android 12+) would replace the brand red with the wallpaper colors.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}