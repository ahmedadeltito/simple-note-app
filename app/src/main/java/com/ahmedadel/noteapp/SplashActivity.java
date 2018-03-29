package com.ahmedadel.noteapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ahmedadel.noteapp.home.mvvm.HomeActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    private Disposable disposable;

    @SuppressLint("RxDefaultScheduler")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        compositeDisposable = new CompositeDisposable();

        Completable.timer(3, TimeUnit.SECONDS)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        startHomeActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "error while opening the home activity screen.");
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }
}
