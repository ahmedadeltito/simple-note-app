package com.ahmedadel.noteapp.home.mvvm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.ahmedadel.noteapp.home.usecase.GetNotesUseCase;

/**
 * Created by Ahmed Adel on 3/28/18.
 */

public class NoteViewModelFactory implements ViewModelProvider.Factory {

    private final GetNotesUseCase getNotesUseCase;

    public NoteViewModelFactory(GetNotesUseCase getNotesUseCase) {
        this.getNotesUseCase = getNotesUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(getNotesUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
