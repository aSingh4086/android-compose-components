package com.liberty.androidcomposecomponents.components.eventCalendar.hooks

import com.liberty.androidcomposecomponents.components.eventCalendar.EventCalendarHoliday
import com.liberty.androidcomposecomponents.components.eventCalendar.model.EventCalendarParam
import com.liberty.androidcomposecomponents.components.eventCalendar.model.WeekDayModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

fun eventCalendarDayModifier(
    days: List<LocalDate?>,
    selectedDate: LocalDate,
    weekends: List<DayOfWeek>,
    currentYearMonth: YearMonth,
): List<WeekDayModel> {
    val weekDays = mutableListOf<WeekDayModel>()
    days.forEachIndexed { index, date ->
        if (date == null) return@forEachIndexed
        val isWeekEnd = weekends.contains(date.dayOfWeek)
        val isInRange = date.month == currentYearMonth.month
        weekDays.add(
            WeekDayModel(
                date = date,
                id = date.dayOfWeek.toString(),
                isInRange = isInRange,
                isToday = date == LocalDate.now(),
                isSelected = date == selectedDate,
                params =
                    EventCalendarParam(
                        isWeekEnd = isWeekEnd,
                        holidays =
                            listOf(
                                EventCalendarHoliday(
                                    name = "Holiday",
                                    date = LocalDate.now(),
                                    description = "",
                                ),
                            ),
                    ),
            ),
        )
    }
    return weekDays
}
