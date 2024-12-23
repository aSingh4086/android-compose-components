package com.liberty.androidcomposecomponents.components.buttons.layouts


import androidx.compose.ui.graphics.Color
import com.liberty.androidcomposecomponents.components.buttons.ButtonAccessType
import com.liberty.androidcomposecomponents.components.buttons.ButtonDefaults

class ButtonLayoutImpl : ButtonLayout {
    override fun getBackgroundColor(
        accessType: ButtonAccessType,
        enabled: Boolean,
    ): Color {
        if (!enabled) {
            return ButtonDefaults.Disabled
        }

        return when (accessType) {
            ButtonAccessType.PRIMARY -> ButtonDefaults.Primary
            ButtonAccessType.SECONDARY -> ButtonDefaults.BackgroundColors.Secondary
            ButtonAccessType.NONE -> ButtonDefaults.NONE
        }
    }

    override fun getContentColor(accessType: ButtonAccessType, enabled: Boolean): Color {
        if (!enabled) {
            return ButtonDefaults.Disabled
        }

        return when (accessType) {
            ButtonAccessType.PRIMARY -> ButtonDefaults.ContentColors.primary
            ButtonAccessType.SECONDARY -> ButtonDefaults.ContentColors.secondary
            ButtonAccessType.NONE -> ButtonDefaults.NONE
        }
    }

    override fun getBorderColor(accessType: ButtonAccessType, enabled: Boolean): Color {
        if (!enabled) {
            return ButtonDefaults.Disabled
        }

        return when (accessType) {
            ButtonAccessType.PRIMARY -> ButtonDefaults.BorderColors.primary
            ButtonAccessType.SECONDARY -> ButtonDefaults.BorderColors.secondary
            ButtonAccessType.NONE -> ButtonDefaults.NONE
        }
    }
}
