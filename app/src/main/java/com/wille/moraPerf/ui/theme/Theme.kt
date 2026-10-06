package com.wille.moraPerf.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

// The app is dark-only, so dynamic color is intentionally not used.
private val MoraColorScheme = darkColorScheme(
    primary = MoraRed,
    onPrimary = MoraBackground,
    secondary = MoraBlue,
    onSecondary = MoraBackground,
    tertiary = MoraYellow,
    onTertiary = MoraBackground,
    background = MoraBackground,
    onBackground = MoraText,
    surface = MoraSurface,
    onSurface = MoraText,
    surfaceVariant = MoraSurfaceAlt,
    onSurfaceVariant = MoraTextMuted,
    outline = MoraOutline,
    outlineVariant = MoraOutline,
)

// The design drops every corner radius. Overriding the whole shape set keeps
// components consistent without touching each one.
private val SquareShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp),
)

@Composable
fun MoraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MoraColorScheme,
        typography = Typography,
        shapes = SquareShapes,
        content = content
    )
}
