package com.akshaw.noteapp.feature_note.domain.use_cases

import com.akshaw.noteapp.feature_note.data.repository.FakeNoteRepository
import com.akshaw.noteapp.feature_note.domain.model.Note
import com.akshaw.noteapp.feature_note.domain.utils.NoteOrderBy
import com.akshaw.noteapp.feature_note.domain.utils.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class GetNotesTest{

    private lateinit var getNotes: GetNotesUseCase
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp(){
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotesUseCase(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed{ index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeRepository.insertNote(it) }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrderBy.Title(OrderType.Ascending)).first()

        for(i in 0..notes.size-2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrderBy.Title(OrderType.Descending)).first()

        for(i in 0..notes.size-2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrderBy.Date(OrderType.Ascending)).first()

        for(i in 0..notes.size-2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrderBy.Date(OrderType.Descending)).first()

        for(i in 0..notes.size-2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrderBy.Color(OrderType.Ascending)).first()

        for(i in 0..notes.size-2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrderBy.Color(OrderType.Descending)).first()

        for(i in 0..notes.size-2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

}