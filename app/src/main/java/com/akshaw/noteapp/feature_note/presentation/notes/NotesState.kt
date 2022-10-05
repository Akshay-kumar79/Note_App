package com.akshaw.noteapp.feature_note.presentation.notes

import com.akshaw.noteapp.feature_note.domain.model.Note
import com.akshaw.noteapp.feature_note.domain.utils.NoteOrderBy
import com.akshaw.noteapp.feature_note.domain.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrderBy: NoteOrderBy = NoteOrderBy.Title(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
