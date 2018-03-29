package com.ahmedadel.noteapp.home.usecase;

import com.ahmedadel.noteapp.UseCase;
import com.ahmedadel.noteapp.home.datasource.repository.GetNotesRepository;
import com.ahmedadel.noteapp.model.Note;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ahmed Adel on 3/28/18.
 */

public class GetNotesUseCase extends UseCase {

    private static GetNotesUseCase INSTANCE;
    private GetNotesRepository getNotesRepository;

    private GetNotesUseCase() {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
        getNotesRepository = GetNotesRepository.getInstance();
    }

    public static synchronized GetNotesUseCase getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GetNotesUseCase();
        return INSTANCE;
    }

    public Flowable<List<Note>> getNotes() {
        return getNotesRepository.getNotes()
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }
}
