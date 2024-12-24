package com.liberty.androidcomposecomponents.components.eventCalendar.model


import com.liberty.androidcomposecomponents.components.eventCalendar.EventCalendarHoliday


data class EventCalendarParam(

    val isWeekEnd: Boolean? = false,
    val holidays: List<EventCalendarHoliday> = emptyList(),
)
