
package com.liberty.androidcomposecomponents.components.eventCalendar.model
import java.time.LocalDate

data class WeekDayModel(
    val date: LocalDate? = null, // 2020-06-29
    val id: String? = "", // MON
    val isInRange: Boolean? = false, // false
    val isToday: Boolean? = false, // false
    val isSelected: Boolean? = false, // false
    val params: EventCalendarParam? = EventCalendarParam(),
)
