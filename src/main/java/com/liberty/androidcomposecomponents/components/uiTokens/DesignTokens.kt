package com.liberty.androidcomposecomponents.components.uiTokens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


object DesignToken {

    fun parseDesignTokenColor(colorString: String): Color =
        if (colorString.startsWith("#")) {
            Color(android.graphics.Color.parseColor(colorString))
        } else if (colorString.startsWith("rgba")) {
            val rgba = colorString.removePrefix("rgba(").removeSuffix(")").split(",")
            Color(
                red = rgba[0].trim().toInt(),
                green = rgba[1].trim().toInt(),
                blue = rgba[2].trim().toInt(),
                alpha = (rgba[3].trim().toFloat() * 255).toInt(),
            )
        } else {
            Color(android.graphics.Color.parseColor("#$colorString"))
        }


    fun createTextStyle(colorMap: Map<String, Color>): Map<String, TextStyle> =
        colorMap.mapValues { (_, color) ->
            TextStyle(color = color)
        }

    fun getTextStyle(
        colorMap: Map<String, Color>,
        key: String,
    ): TextStyle? = colorMap[key]?.let { TextStyle(color = it) }

    object ColorToken {
        val color_aqua_haze = Color(0XFFedf6f4)
        val color_edge_water = Color(0XFFc9e4dd)
        val color_genoa = Color(0XFF1b7f66)

        val color_wisp_pink = Color(0XFFfdf2f3)
        val color_azalea = Color(0XFFf8cfd3)
        val color_crimson = Color(0XFFdc0c22)
        val color_linen = Color(0XFFfcf6ed)
        val color_sidecar = Color(0XFFf5e2c9)
        val color_indochine = Color(0XFFcc7300)
        val color_chelsea_gem = Color(0XFFa35c00)

        val color_zircon = Color(0XFFf4f6ff)
        val color_titan_white = Color(0XFFdee4ff)
        val color_cornflower_blue = Color(0XFF667fff)

        val color_deep_cerulean = Color(0XFF0176b2)
        val color_all_ports = Color(0XFF016fa7)
        val color_orient = Color(0XFF005680)
        val color_orient_2 = Color(0XFF005c89)
        val color_secondary = Color(0xFF005151)
        val sys_color_border_lowEmphasis_onLight = Color(0x1A000000)
        val sys_color_text_highEmphasis_onLight = Color(0xDE000000)
        val sys_color_text_lowEmphasis_onLight = Color(0x99000000)
        val sys_color_surface_lightDarker = Color(0xFFF5F5F5)
        val sys_color_theme_primary = Color(0xFF005151)

        val no_preset_background = Color(0xFFEFEFEF)
        val dark = Color(0xFF000000)
        val no_preset_hover = Color(0x0D000000)
        val sys_color_rowHighlight = Color(0xfff8f9fa)

        val sys_color_surfaceTag_success_light = Color(0xFFD3EDE4)
        val sys_color_tableHeaderIdle = Color(0xFFE6E6E6)
        val sys_color_important = Color(0xFFdc0c22)
        val sys_color_success = Color(0xFF008060)
        val sys_color_text_action_enabled = Color(0XFF016ca2)
        val tertiary2_color = Color(0xFFE7F4FD)
    }

    object FontColorToken {
        val sys_color_text_lowEmphasis_onLight = Color(0x99000000)
        val sys_color_text_highEmphasis_onLight = Color(0xDE000000)
        val font_dark = Color(0xFF000000)
        val color_titan_white = Color(0XFFdee4ff)
    }

    object BrandColorToken {
        val brand_silver = Color(0XFFCCCCCC)
        val brand_color_mercury = Color(0XFFEEEEEE)
        val brand_color_mantis = Color(0XFF6bb34d)
        val brand_color_dove_gray = Color(0xFF666666)
        val brand_color_boulder = Color(0xFF757575)
        val brand_color_gallery = Color(0xFFF8F8F8)
        val brand_color_alto = Color(0xffbbbbbb)
    }
}
