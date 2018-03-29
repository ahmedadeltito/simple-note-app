package com.ahmedadel.noteapp.home.datasource.repository;

import com.ahmedadel.noteapp.model.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * RemoteNoteRepository is the repository interface that will be the implementer of the remote note data source.
 */

public interface RemoteNoteRepository {

    Completable add(Note note);

    Completable update(Note note);

    Completable delete(String noteId);

    Flowable<List<Note>> getNotes();

}
