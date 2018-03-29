package com.ahmedadel.noteapp.home.datasource.repository;

import com.ahmedadel.noteapp.model.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * LocalNoteRepository is the repository interface that will be the implementer of the local note data source.
 */

public interface LocalNoteRepository {

    Completable add(Note note);

    Completable addNotes(List<Note> noteList);

    Completable update(Note note);

    Completable delete(String noteId);

    Flowable<List<Note>> getNotes();

}
