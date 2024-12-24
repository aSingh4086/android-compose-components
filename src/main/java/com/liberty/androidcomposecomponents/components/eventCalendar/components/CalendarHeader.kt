package com.liberty.androidcomposecomponents.components.eventCalendar.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.liberty.androidcomposecomponents.components.TextField
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Collections
import java.util.Locale

@Composable
fun CalendarHeader(
    modifier: Modifier,
    startDay: DayOfWeek = DayOfWeek.MONDAY,
    isWeekTotalVisible: Boolean = true,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val rotationDistance = getRotationDistance(startDay)
        val daysOfWeek = DayOfWeek.entries.rotate(rotationDistance)
        daysOfWeek.forEach {
            CalendarHeaderItem(modifier = modifier, content = {
                TextField(
                    text =
                        it.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                )
            })
        }

        if (isWeekTotalVisible) {
            CalendarHeaderItem(modifier = modifier, content = {
                TextField(
                    text = "Total",
                )
            })
        }
    }
}

@Composable
fun RowScope.CalendarHeaderItem(
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier =
            Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                ).minimumInteractiveComponentSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

@Composable
@Preview
fun previewCalendarHeader() {
    CalendarHeader(modifier = Modifier)
}

fun <T> List<T>.rotate(distance: Int): List<T> {
    val list = this.toMutableList()
    Collections.rotate(list, distance)
    return list
}

fun getRotationDistance(startDay: DayOfWeek): Int {
    val daysOfWeek = DayOfWeek.entries
    val startDayIndex = 8 - startDay.value

    return if (startDayIndex != -1) startDayIndex else 0
}
