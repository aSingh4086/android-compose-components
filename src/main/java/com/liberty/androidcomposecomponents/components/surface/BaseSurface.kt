package com.liberty.androidcomposecomponents.components.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.liberty.androidcomposecomponents.components.uiTokens.DesignToken

@Composable
@NonRestartableComposable
fun BaseSurface(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    shape: Shape = RectangleShape,
    contentColor: Color = Color.Blue,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit,
) {
    val LocalAbsoluteTonalElevation = compositionLocalOf { 0.dp }
    val absoluteElevation = LocalAbsoluteTonalElevation.current + tonalElevation

    val pressed by interactionSource.collectIsPressedAsState()

    val indentationBackground =
        if (pressed) {
            DesignToken.ColorToken.tertiary2_color
        } else {
            backgroundColor
        }

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalAbsoluteTonalElevation provides absoluteElevation,
    ) {
        Box(
            modifier =
            modifier.then(
                Modifier
                    .surface(
                        shape = shape,
                        backgroundColor = indentationBackground,
                        border = border,
                        shadowElevation = shadowElevation,
                    ).clickable(
                        enabled = enabled,
                        onClick = onClick,
                        interactionSource = interactionSource,
                        indication = LocalIndication.current,
                    ),
            ),
            propagateMinConstraints = true,
        ) {
            content()
        }
    }
}

private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Dp,
) = this
    .shadow(shadowElevation, shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)

@Composable
fun BaseSurface(
    modifier: Modifier,
    backgroundColor: Color,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
        modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor),
    ) {
        content()
    }
}
