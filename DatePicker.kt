package com.personal.samples.components.dates.datepicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.personal.samples.R
import com.personal.samples.components.TextField
import com.personal.samples.components.baseInput.BaseInput
import com.personal.samples.components.baseInput.BaseInputType
import com.personal.samples.components.button.Button
import com.personal.samples.components.cards.styles.CardStyles
import com.personal.samples.components.dates.utils.DatePickerUtils.getDaysInMonth
import com.personal.samples.components.iconButton.IconButton
import com.personal.samples.components.iconButton.IconButtonImage
import com.personal.samples.components.layout.LayoutColumn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DatePickerDialog(
    onDateChange: (selectedData: String) -> Unit,
    onClose: () -> Unit,
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentYearMonth by remember { mutableStateOf((YearMonth.now())) }
    Box(
        modifier =
            Modifier
                .border(
                    border = BorderStroke(1.dp, CardStyles.ColorToken.c_card_silver_border_color),
                    shape =
                        RoundedCornerShape(
                            CornerSize(4.dp),
                        ),
                ).background(shape = RoundedCornerShape(corner = CornerSize(4.dp)), color = Color.White)
                .padding(20.dp),
    ) {
        Column {
            DatePickerHeader(
                currentYearMonth = currentYearMonth,
                onPreviousMonth = {
                    currentYearMonth = currentYearMonth.minusMonths(1)
                },
                onNextMonth = {
                    currentYearMonth = currentYearMonth.plusMonths(1)
                },
            )

            DatePickerUI(yearMonth = currentYearMonth, selectedDate = selectedDate) {
                selectedDate = it
                onDateChange(it.toString())
            }

            DatePickerFooter(onClose = onClose)
        }
    }
}

@Composable
fun DatePickerHeader(
    currentYearMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    val selectedYearMonth = currentYearMonth.year
    val selectedMonthDisplayName =
        currentYearMonth.month.getDisplayName(
            TextStyle.SHORT,
            Locale.getDefault(),
        )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(content = {
            IconButtonImage(imageId = R.drawable.icon_back_link_arrow)
        }, onClick = {
            onPreviousMonth()
        })
        TextField(
            text = "$selectedMonthDisplayName $selectedYearMonth",
        )
        IconButton(content = {
            IconButtonImage(imageId = R.drawable.icon_link_arrow)
        }, onClick = {
            onNextMonth()
        })
    }
}

@Composable
fun DatePickerUI(
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    val daysInMonth = getDaysInMonth(yearMonth)
    Column {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(DayOfWeek.values(), itemContent = { dayOfWeek ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    TextField(
                        text =
                            dayOfWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.getDefault(),
                            ),
                    )
                }
            })
        }

        LazyColumn {
            items(daysInMonth.chunked(7)) { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (day in week) {
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clickable {
                                        if (day != null) onDateSelected(day)
                                    }.background(
                                        if (day == selectedDate) Color.Cyan else Color.Transparent,
                                    ),
                            contentAlignment = Alignment.Center,
                        ) {
                            TextField(text = day?.dayOfMonth?.toString() ?: "")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DatePickerFooter(onClose: () -> Unit, onOk: () -> Unit = {}) {
    LayoutColumn(modifier = Modifier, spacing = 5.dp) {
        Button(onClick = onClose) {
            TextField("Cancel")
        }
        Button(onClick = { onOk() }) {
            TextField("Ok")
        }
    }
}

@Composable
fun DatePicker(
    label: String = "Date",
    value: String? = null,
    onDateChange: (selectedData: String) -> Unit,
) {
    var isDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }

    fun handleDateChange(selectedDate: String) {
        onDateChange(selectedDate)
        isDialogOpen = false
    }

    BaseInput(
        inputType = BaseInputType.Date,
        value = value,
        onValueChange = onDateChange,
        label = label,
        trailingIcon = {
            IconButton(content = {
                IconButtonImage(imageId = R.drawable.calendar)
            }, onClick = {
                isDialogOpen = true
            })
        },
    )

    if (isDialogOpen) {
        Dialog(onDismissRequest = { isDialogOpen = false }) {
            DatePickerDialog(::handleDateChange, onClose = {
                isDialogOpen = false
            })
        }
    }
}

@Composable
@Preview
fun DatePickerSample() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DatePickerDialog(
            onDateChange = { },
            onClose = { },
        )
    }
}
