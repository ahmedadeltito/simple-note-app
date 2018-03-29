package com.ahmedadel.noteapp.home.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ahmedadel.noteapp.home.usecase.GetNotesUseCase;
import com.ahmedadel.noteapp.model.Note;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import butterknife.Unbinder;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Ahmed Adel on 3/28/18.
 */

public class HomeViewModel extends ViewModel {

    private final GetNotesUseCase getNotesUseCase;

    private MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    HomeViewModel(GetNotesUseCase getNotesUseCase) {
        this.getNotesUseCase = getNotesUseCase;
        fetchNotes();
    }

    void fetchNotes() {
        isLoading.setValue(true);
        getNotesUseCase.addObserver(getNotesUseCase
                .getNotes()
                .doFinally(() -> isLoading.setValue(false))
                .subscribe(noteList -> notesLiveData.setValue(noteList),
                        throwable -> error.setValue(throwable.getMessage())));
    }

    MutableLiveData<List<Note>> getNotes() {
        return notesLiveData;
    }

    LiveData<Boolean> isLoading() {
        return isLoading;
    }

    MutableLiveData<String> getError() {
        return error;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getNotesUseCase.dispose();
    }
}
