package com.akshaw.noteapp.feature_note.presentation.notes.components

import android.provider.ContactsContract
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akshaw.noteapp.feature_note.domain.utils.NoteOrderBy
import com.akshaw.noteapp.feature_note.domain.utils.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrderBy: NoteOrderBy = NoteOrderBy.Date(OrderType.Descending),
    onOrderChange: (NoteOrderBy) -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrderBy is NoteOrderBy.Title,
                onSelect = { onOrderChange(NoteOrderBy.Title(noteOrderBy.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrderBy is NoteOrderBy.Date,
                onSelect = { onOrderChange(NoteOrderBy.Date(noteOrderBy.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrderBy is NoteOrderBy.Color,
                onSelect = { onOrderChange(NoteOrderBy.Color(noteOrderBy.orderType)) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrderBy.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrderBy.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrderBy.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrderBy.copy(OrderType.Descending))
                }
            )
        }
    }
}