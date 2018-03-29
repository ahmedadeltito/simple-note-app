package com.ahmedadel.noteapp.home.datasource.repository;

import com.ahmedadel.noteapp.home.datasource.LocalNoteDataSource;
import com.ahmedadel.noteapp.home.datasource.RemoteNoteDataSource;
import com.ahmedadel.noteapp.model.Note;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Ahmed Adel on 3/28/18.
 */

public class GetNotesRepository {

    private static GetNotesRepository INSTANCE;
    private LocalNoteDataSource local;
    private RemoteNoteDataSource remote;
    private List<Note> cache = new ArrayList<>();

    private GetNotesRepository() {
        this.remote = new RemoteNoteDataSource();
        this.local = new LocalNoteDataSource();
    }

    public static synchronized GetNotesRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GetNotesRepository();
        return INSTANCE;
    }

    public Flowable<List<Note>> getNotes() {
        return Flowable.concat(
                !cache.isEmpty() ?
                        Flowable.just(cache) :
                        local.getNotes()
                                .onErrorResumeNext(throwable -> {
                                    if (throwable instanceof UnknownHostException)
                                        return Flowable.empty();
                                    return Flowable.error(throwable);
                                })
                                .doOnNext(noteList -> {
                                    cache.clear();
                                    cache.addAll(noteList);
                                }),
                remote.getNotes()
                        .onErrorResumeNext(throwable -> {
                            if (throwable instanceof UnknownHostException)
                                return Flowable.empty();
                            return Flowable.error(throwable);
                        })
                        .doOnNext(notes -> {
                            cache.clear();
                            cache.addAll(notes);
                            addNotes(notes);
                        })
        );
    }

    private void addNotes(List<Note> notes) {
        if (local != null) {
            local.addNotes(notes)
                    .subscribe(new CompletableObserver() {
                        Disposable disposable;

                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onComplete() {
                            Timber.i("notes are saved successfully.");
                            disposable.dispose();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "error happen during the save notes process.");
                            disposable.dispose();
                        }
                    });
        }
    }
}
