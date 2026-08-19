package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = SpsiPrimaryDark,
    onPrimary = SpsiOnPrimaryDark,
    primaryContainer = SpsiPrimaryContainerDark,
    onPrimaryContainer = SpsiOnPrimaryContainerDark,
    secondary = SpsiSecondaryDark,
    onSecondary = SpsiOnSecondaryDark,
    secondaryContainer = SpsiSecondaryContainerDark,
    onSecondaryContainer = SpsiOnSecondaryContainerDark,
    tertiary = SpsiTertiaryDark,
    onTertiary = SpsiOnTertiaryDark,
    tertiaryContainer = SpsiTertiaryContainerDark,
    onTertiaryContainer = SpsiOnTertiaryContainerDark,
    background = SpsiBackgroundDark,
    onBackground = SpsiOnBackgroundDark,
    surface = SpsiSurfaceDark,
    onSurface = SpsiOnSurfaceDark,
    surfaceVariant = SpsiSurfaceVariantDark,
    onSurfaceVariant = SpsiOnSurfaceVariantDark,
    outline = SpsiOutlineDark
  )

private val LightColorScheme =
  lightColorScheme(
    primary = SpsiPrimaryLight,
    onPrimary = SpsiOnPrimaryLight,
    primaryContainer = SpsiPrimaryContainerLight,
    onPrimaryContainer = SpsiOnPrimaryContainerLight,
    secondary = SpsiSecondaryLight,
    onSecondary = SpsiOnSecondaryLight,
    secondaryContainer = SpsiSecondaryContainerLight,
    onSecondaryContainer = SpsiOnSecondaryContainerLight,
    tertiary = SpsiTertiaryLight,
    onTertiary = SpsiOnTertiaryLight,
    tertiaryContainer = SpsiTertiaryContainerLight,
    onTertiaryContainer = SpsiOnTertiaryContainerLight,
    background = SpsiBackgroundLight,
    onBackground = SpsiOnBackgroundLight,
    surface = SpsiSurfaceLight,
    onSurface = SpsiOnSurfaceLight,
    surfaceVariant = SpsiSurfaceVariantLight,
    onSurfaceVariant = SpsiOnSurfaceVariantLight,
    outline = SpsiOutlineLight
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Use curated SPSI tricolor theme by default for unified organizational identity
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
