package com.ahmedadel.noteapp;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * UseCase is the parent use case class that handling the process of adding and removing disposables from presentation
 * layer.
 */

public abstract class UseCase {

    protected final Scheduler executorThread;
    protected final Scheduler uiThread;
    private final CompositeDisposable compositeDisposable;

    protected  UseCase(Scheduler executorThread, Scheduler uiThread) {
        this.executorThread = executorThread;
        this.uiThread = uiThread;
        compositeDisposable = new CompositeDisposable();
    }

    public void addObserver(Disposable observer) {
        compositeDisposable.add(observer);
    }

    public void dispose() {
        compositeDisposable.clear();
    }
}
