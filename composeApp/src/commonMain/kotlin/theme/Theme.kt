
package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = customBackGround,
    onPrimary = PrimaryTextColor,
    secondary = SecondaryColor,
    onSecondary = SecondaryTextColor,
    tertiary = iconColor,
    onTertiary = PrimaryTextColor,
    background = BackgroundLightColor,
    onBackground = Color.Black,
    surface = SurfaceLight,
    onSurface = Color.Black,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = Color.Black,
    secondaryContainer = PrimaryColor,
    onSecondaryContainer = Color.White,
    error = ErrorColor,
    onError = OnErrorColor,
)

private val DarkColors = darkColorScheme(
    primary = customBackGround,
    onPrimary = PrimaryTextColor,
    secondary = SecondaryLightColor,
    onSecondary = SecondaryTextColor,
    tertiary = iconColor,
    onTertiary = PrimaryTextColor,
    background = BackgroundDarkColor,
    onBackground = Color.White,
    surface = SurfaceDark,
    onSurface = Color.White,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = Color.White,
    secondaryContainer = PrimaryColor,
    onSecondaryContainer = Color.White,
    error = ErrorColor,
    onError = OnErrorColor,
)

@Composable
internal fun ComposeExperimentalTheme(useDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val autoColors = if (useDarkTheme)LightColors else LightColors
    MaterialTheme(
        colorScheme = autoColors,
        shapes = Shapes,
        content = content,
    )
}
