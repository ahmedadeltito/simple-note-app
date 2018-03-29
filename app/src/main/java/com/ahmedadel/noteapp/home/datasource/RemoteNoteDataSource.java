package com.ahmedadel.noteapp.home.datasource;

import com.ahmedadel.noteapp.home.datasource.repository.RemoteNoteRepository;
import com.ahmedadel.noteapp.model.Note;
import com.ahmedadel.noteapp.network.ApiClient;
import com.ahmedadel.noteapp.network.service.NoteService;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Ahmed Adel on 3/28/18.
 */

public class RemoteNoteDataSource implements RemoteNoteRepository {

    private NoteService service;

    public RemoteNoteDataSource() {
        service = ApiClient.getInstance().getClient().create(NoteService.class);
    }

    @Override
    public Completable add(Note note) {
        return service.add(note);
    }

    @Override
    public Completable update(Note note) {
        return service.update(note);
    }

    @Override
    public Completable delete(String noteId) {
        return service.delete(noteId);
    }

    @Override
    public Flowable<List<Note>> getNotes() {
        return service.getNotes();
    }
}
