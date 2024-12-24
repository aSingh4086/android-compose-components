package com.liberty.androidcomposecomponents.components.eventCalendar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liberty.androidcomposecomponents.R
import com.liberty.androidcomposecomponents.components.TextField
import com.liberty.androidcomposecomponents.components.eventCalendar.components.CalendarHeader
import com.liberty.androidcomposecomponents.components.eventCalendar.hooks.eventCalendarDayModifier
import com.liberty.androidcomposecomponents.components.eventCalendar.utils.CalendarUtils.getDaysInMonth
import com.liberty.androidcomposecomponents.components.uiTokens.DesignToken
import com.liberty.androidcomposecomponents.components.eventCalendar.model.WeekDayModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

data class EventCalendarHoliday(
    val date: LocalDate,
    val name: String,
    val description: String,
)

data class CalendarEvents(
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    val type: String,
)

data class EventsCalendarEventConfig(
    val type: String,
    val label: String,
    val modifier: Modifier,
)

data class CalendarDayItemModel(
    val date: LocalDate,
    val id: String,
    val isInRange: Boolean,
    val isToday: Boolean,
    val isWeekend: Boolean,
    val holidays: List<EventCalendarHoliday> = emptyList(),
)

enum class CalendarViewType {
    YEAR,
    MONTH,
    WEEK,
}

object EventsCalendarConstants {
    const val WEEK_DAYS = 7
}

@Composable
fun EventsCalendar(
    yearMonth: YearMonth = YearMonth.now(),
    selectedDate: LocalDate = LocalDate.now(),
    updateSelectedDate: (LocalDate) -> Unit = {},
    viewType: CalendarViewType = CalendarViewType.MONTH,
    startDay: DayOfWeek = DayOfWeek.SUNDAY,
    weekends: List<DayOfWeek> = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
    holidays: List<EventCalendarHoliday> =
        listOf(
            EventCalendarHoliday(
                date = LocalDate.now(),
                name = "Test",
                description = "Test",
            ),
        ),
    calendarEvents: List<CalendarEvents> =
        listOf(
            CalendarEvents(
                dateFrom = LocalDate.now(),
                dateTo = LocalDate.now(),
                type = "Test",
            ),
        ),
    eventConfig: EventsCalendarEventConfig =
        EventsCalendarEventConfig(
            type = "Test",
            label = "Test",
            modifier = Modifier,
        ),
) {
    Box(
        modifier =
        Modifier
            .heightIn(min = 500.dp)
            .background(Color.White),
    ) {
        when (viewType) {
            CalendarViewType.MONTH -> {
                MonthlyCalendar(
                    startDay = startDay,
                    currentYearMonth = yearMonth,
                    holidays = holidays,
                    events = calendarEvents,
                    weekends = weekends,
                    updateSelectedDate = { localDate ->
                        updateSelectedDate(localDate)
                    },
                )
            }

            CalendarViewType.WEEK -> {
                WeekCalendar(
                    selectedDate = selectedDate,
                    startDay = startDay,
                    weekends = weekends,
                    updateSelectedDate = { localDate ->
                        updateSelectedDate(localDate)
                    },
                )
            }

            CalendarViewType.YEAR -> {
                YearlyCalendar(
                    selectedDate = selectedDate,
                    weekends = weekends,
                    updateSelectedDate = { localDate ->
                        updateSelectedDate(localDate)
                    },
                )
            }
        }
    }
}

@Composable
fun YearlyCalendar(
    selectedDate: LocalDate = LocalDate.now(),
    weekends: List<DayOfWeek> = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
    updateSelectedDate: (LocalDate) -> Unit = {},
) {
    val months = Month.entries.map { YearMonth.of(selectedDate.year, it) }
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(months) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TextButton(
                    onClick = {},
                    content = {
                        TextField(
                            text = it.month.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(),
                        )
                    },
                )
            }
            Box(modifier = Modifier.padding(top = 40.dp)) {
                MonthlyCalendar(
                    currentYearMonth = it,
                    weekends = weekends,
                    updateSelectedDate = { localDate ->
                        updateSelectedDate(localDate)
                    },
                )
            }
        }
    }
}

@Composable
fun MonthlyCalendar(
    startDay: DayOfWeek = DayOfWeek.SUNDAY,
    selectedDate: LocalDate = LocalDate.now(),
    currentYearMonth: YearMonth = YearMonth.now(),
    holidays: List<EventCalendarHoliday> = emptyList(),
    events: List<CalendarEvents> = emptyList(),
    rangeSelectionStartDate: LocalDate? = null,
    rangeSelectionEndDate: LocalDate? = null,
    isWeekTotalVisible: Boolean = true,
    weekends: List<DayOfWeek> = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
    updateSelectedDate: (LocalDate) -> Unit = {},
) {
    val days = getDaysInMonth(currentYearMonth)
    val weekDays =
        eventCalendarDayModifier(
            days = days,
            selectedDate = selectedDate,
            currentYearMonth = currentYearMonth,
            weekends = weekends,
        )
    val weeks = weekDays.chunked(EventsCalendarConstants.WEEK_DAYS)
    Column(
        modifier = Modifier.heightIn(max = 500.dp),
    ) {
        Box(modifier = Modifier) {
            CalendarHeader(
                modifier = Modifier,
                startDay = startDay,
                isWeekTotalVisible = isWeekTotalVisible,
            )
        }
        weeks.forEach { it ->
            WeeklyView(
                days = it,
                updateSelectedDate = { localDate ->
                    updateSelectedDate(localDate)
                },
            )
        }
    }
}

@Composable
fun WeekCalendar(
    startDay: DayOfWeek = DayOfWeek.SUNDAY,
    weekends: List<DayOfWeek> = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
    selectedDate: LocalDate = LocalDate.now(),
    rangeSelectionStartDate: LocalDate? = null,
    rangeSelectionEndDate: LocalDate? = null,
    updateSelectedDate: (LocalDate) -> Unit = {},
) {
    val weekDays =
        selectedDate
            .minusDays(selectedDate.dayOfWeek.value.toLong())
            .let { startOfWeek ->
                (0 until EventsCalendarConstants.WEEK_DAYS).map {
                    startOfWeek.plusDays(it.toLong())
                }
            }.let {
                eventCalendarDayModifier(
                    days = it,
                    selectedDate = selectedDate,
                    currentYearMonth = YearMonth.now(),
                    weekends = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                )
            }
    Column(
        modifier =
        Modifier,
    ) {
        Box(modifier = Modifier) {
            CalendarHeader(modifier = Modifier, startDay)
        }
        WeeklyView(
            days = weekDays,
            updateSelectedDate = { localDate ->
                updateSelectedDate(localDate)
            },
        )
    }
}

@Composable
fun ColumnScope.WeeklyView(
    days: List<WeekDayModel?> = listOf(),
    isWeekTotalVisible: Boolean = true,
    updateSelectedDate: (LocalDate) -> Unit = {},
) {
    Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.Center,
    ) {
        days.filterNotNull().forEach { it ->
            CalendarDay(
                day = it,
                onDateChange = { date ->
                    updateSelectedDate(date)
                },
            )
        }
        if (isWeekTotalVisible) {
            Box(
                modifier =
                Modifier
                    .weight(1f)
                    .background(
                        color = colorResource(R.color.selago)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.border_low)
                    ),
            ) {
                TextField(modifier = Modifier.fillMaxSize(), text = "test")
            }
        }
    }
}

@Composable
fun RowScope.CalendarDay(
    onDateChange: (LocalDate) -> Unit = {},
    day: WeekDayModel,
) {
    val backgroundColor by animateColorAsState(
        targetValue =
        if (day.params?.isWeekEnd == true) {
            DesignToken.BrandColorToken.brand_color_mercury
        } else if (day.isInRange == true) {
            DesignToken.BrandColorToken.brand_color_gallery
        } else {
            Color.Transparent
        },
        label = "",
    )
    Box(
        modifier =
        Modifier
            .weight(1f)
            .clickable {
                day.date?.let { onDateChange(it) }
            }
            .background(
                backgroundColor,
            )
            .border(
                width = 1.dp,
                color = DesignToken.ColorToken.sys_color_border_lowEmphasis_onLight,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (day.params?.holidays?.isNotEmpty() == true) {
            GradientBackground()
        }

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            TextField(
                text = day.date?.dayOfMonth.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp, top = 5.dp),
            )
            EventMoreItem(itemCount = 2)
        }
    }
}

@Composable
fun RowScope.EventCalendarDayItem(
    onDateChange: (LocalDate) -> Unit = {},
    day: LocalDate,
    isWeekend: Boolean = false,
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier =
        Modifier
            .weight(1f)
            .clickable {
                if (!isWeekend) {
                    onDateChange(day)
                }
            }
            .background(
                backgroundColor,
            )
            .border(
                width = 1.dp,
                color = colorResource(R.color.border_low)
            ),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
fun EventMoreItem(itemCount: Int = 0) {
    Box(modifier = Modifier, contentAlignment = Alignment.BottomStart) {
        TextField(
            text = "$itemCount+",
            fontSize = 10.sp,
            modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
        )
    }
}

@Composable
fun GradientBackground() {
    val gradient =
        Brush.linearGradient(
            colors =
            listOf(
                Color(0xFF95004A),
                Color(0xFF95004A),
                Color(0xFFFF62B0),
                Color(0xFFFF62B0),
            ),
            start = Offset(0f, 0f),
            end = Offset(20f, 20f),
            tileMode = TileMode.Repeated,
        )

    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(gradient),
    )
}

@Preview
@Composable
fun PreviewEventsCalendar() {
    EventsCalendar()
}
