package com.ahmedadel.noteapp.home.datasource;

import com.ahmedadel.noteapp.home.datasource.repository.LocalNoteRepository;
import com.ahmedadel.noteapp.model.Note;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * LocalNoteDataSource is the implementer of the local note repository.
 */

public class LocalNoteDataSource implements LocalNoteRepository {

    private Realm realm;

    @Override
    public Completable add(Note note) {
        realm = Realm.getDefaultInstance();
        return Completable.fromAction(() -> {
            realm.executeTransaction(realm -> realm.insertOrUpdate(note));
        });
    }

    @Override
    public Completable addNotes(List<Note> noteList) {
        realm = Realm.getDefaultInstance();
        return Completable.fromAction(() -> realm.executeTransaction(realm -> realm.insertOrUpdate(noteList)));
    }

    @Override
    public Completable update(Note note) {
        realm = Realm.getDefaultInstance();
        return Completable.fromAction(() -> {
            realm.executeTransaction(realm -> realm.insertOrUpdate(note));
        });
    }

    @Override
    public Completable delete(String noteId) {
        realm = Realm.getDefaultInstance();
        return Completable.fromAction(() -> {
            Note note = realm.where(Note.class).equalTo("id", noteId).findFirst();
            if (note != null && note.isValid()) {
                note.deleteFromRealm();
            }
        });
    }

    @Override
    public Flowable<List<Note>> getNotes() {
        realm = Realm.getDefaultInstance();
        RealmResults<Note> noteRealmResults = realm.where(Note.class).findAll();
        return !noteRealmResults.isEmpty() ?
                Flowable.just(new ArrayList<>(noteRealmResults)) :
                Flowable.empty();
    }
}
