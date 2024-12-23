package com.liberty.androidcomposecomponents.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.liberty.androidcomposecomponents.components.uiTokens.DesignToken

object ButtonDefaults {
    val MinWidth = 58.dp
    val MinHeight = 40.dp
    val Primary = DesignToken.ColorToken.color_all_ports
    val Secondary = DesignToken.ColorToken.color_secondary
    val Disabled = DesignToken.BrandColorToken.brand_color_dove_gray
    val NONE = Color.White
    val ContentColor = Color.White

    private val ButtonHorizontalPadding = 24.dp
    private val ButtonVerticalPadding = 8.dp

    val ContentPadding =
        PaddingValues(
            start = ButtonHorizontalPadding,
            top = ButtonVerticalPadding,
            end = ButtonHorizontalPadding,
            bottom = ButtonVerticalPadding,
        )

    object BackgroundColors {
        val Secondary = Color.White
    }

    object ContentColors {
        val secondary = DesignToken.ColorToken.color_secondary
        val primary = DesignToken.ColorToken.color_titan_white
    }

    object BorderColors {
        val primary = DesignToken.ColorToken.color_titan_white
        val secondary = DesignToken.ColorToken.color_secondary
    }
}
