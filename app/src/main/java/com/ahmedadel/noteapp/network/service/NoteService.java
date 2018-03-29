package com.ahmedadel.noteapp.network.service;

import com.ahmedadel.noteapp.model.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * NoteService is the service interface class that contains all the CRUD end points of note APIs.
 */

public interface NoteService {

    @POST("notes")
    Completable add(@Body Note note);

    @PUT("notes")
    Completable update(@Body Note note);

    @DELETE("notes")
    Completable delete(String noteId);

    @GET("notes")
    Flowable<List<Note>> getNotes();

}
