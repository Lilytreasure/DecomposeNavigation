
package theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import platform.font

@Composable
internal fun getTypography(): Typography {
    val poppinsRegular =
        font(
            "poppins",
            "poppins_regular",
            FontWeight.Normal,
            FontStyle.Normal,
        )

    val poppinsBold =
        font(
            "poppins",
            "poppins_bold",
            FontWeight.Bold,
            FontStyle.Normal,
        )

    val poppinsLight =
        font(
            "poppins",
            "poppins_light",
            FontWeight.Light,
            FontStyle.Normal,
        )

    val poppinsMedium =
        font(
            "poppins",
            "poppins_medium",
            FontWeight.Medium,
            FontStyle.Normal,
        )

    val poppinsSemiBold =
        font(
            "poppins",
            "poppins_semiBold",
            FontWeight.SemiBold,
            FontStyle.Normal,
        )

    val poppinsThin =
        font(
            "poppins",
            "poppins_thin",
            FontWeight.Thin,
            FontStyle.Normal,
        )

    val poppinsExtraBold =
        font(
            "poppins",
            "poppins_extraBold",
            FontWeight.ExtraBold,
            FontStyle.Normal,
        )

    val poppinsExtraLight =
        font(
            "poppins",
            "poppins_extraLight",
            FontWeight.ExtraLight,
            FontStyle.Normal,
        )
    val poppinsBlack = font(
        "poppins",
        "poppins_black",
        FontWeight.Black,
        FontStyle.Normal,
    )

    @Composable
    fun poppins() = FontFamily(
       poppinsThin,
       poppinsExtraLight,
       poppinsLight,
       poppinsRegular,
       poppinsMedium,
       poppinsSemiBold,
       poppinsBold,
       poppinsExtraBold,
       poppinsBlack,
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 50.sp,
            // lineHeight = 64.sp,
            // letterSpacing = (_0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 40.sp,
            // lineHeight = 52.sp,
        ),
        displaySmall = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 30.sp,
            // lineHeight = 44.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 28.sp,
            // lineHeight = 40.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 24.sp,
            // lineHeight = 36.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            // lineHeight = 32.sp,
        ),
        titleLarge = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            // lineHeight = 28.sp,
        ),
        titleMedium = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
            // lineHeight = 24.sp,
            // letterSpacing = 0.1.sp,
        ),
        titleSmall = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.1.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            // lineHeight = 24.sp,
            // letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
            // lineHeight = 16.sp,
            // letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
            // lineHeight = 16.sp,
            // letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily =poppins(),
            fontWeight = FontWeight.W500,
            fontSize = 9.sp,
            // lineHeight = 16.sp,
        ),
    )
}
