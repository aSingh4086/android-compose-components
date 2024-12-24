package com.liberty.androidcomposecomponents.components.eventCalendar.utils

import java.time.LocalDate
import java.time.YearMonth

object CalendarUtils {
    fun getDaysInMonth(selectedMonth: YearMonth): List<LocalDate?> {

        val daysInCurrentMonth = selectedMonth.atEndOfMonth().dayOfMonth
        val firstDayOfMonth = selectedMonth.atDay(1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

        val previousMonth = selectedMonth.minusMonths(1)
        val daysInPreviousMonth = previousMonth.atEndOfMonth().dayOfMonth
        val prevMonthDays =
            (daysInPreviousMonth - firstDayOfWeek + 1..daysInPreviousMonth).map {
                previousMonth.atDay(it)
            }
        val nextMonth = selectedMonth.plusMonths(1)

        val totalCells = 35
        val currentMonthDays = (1..daysInCurrentMonth).map { selectedMonth.atDay(it) }
        val nextMonthDays =
            (1..totalCells - (currentMonthDays.size + prevMonthDays.size)).map {
                nextMonth.atDay(it)
            }

        val calendarDays = prevMonthDays + currentMonthDays + nextMonthDays

        return calendarDays
    }
}