package com.liberty.androidcomposecomponents.components.buttons.layouts


import androidx.compose.ui.graphics.Color
import com.liberty.androidcomposecomponents.components.buttons.ButtonAccessType

interface ButtonLayout {
    companion object {
        private var INSTANCE: ButtonLayoutImpl? = null
        val instance: ButtonLayoutImpl?
            get() {
                if (INSTANCE == null) {
                    // synchronized creates a lock and no other process can access it other
                    // than the one causing the lock until the instance is set
                    synchronized(ButtonLayout::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = ButtonLayoutImpl()
                        }
                    }
                }
                return INSTANCE
            }
    }

    fun getBackgroundColor(
        accessType: ButtonAccessType,
        enabled: Boolean,
    ): Color

    fun getContentColor(
        accessType: ButtonAccessType,
        enabled: Boolean,
    ): Color

    fun getBorderColor(
        accessType: ButtonAccessType,
        enabled: Boolean,
    ): Color
}
