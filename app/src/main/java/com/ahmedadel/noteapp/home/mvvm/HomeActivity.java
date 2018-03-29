package com.ahmedadel.noteapp.home.mvvm;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ahmedadel.noteapp.BaseActivity;
import com.ahmedadel.noteapp.R;
import com.ahmedadel.noteapp.home.adapter.NoteAdapter;
import com.ahmedadel.noteapp.home.usecase.GetNotesUseCase;
import com.ahmedadel.noteapp.model.Note;
import com.ahmedadel.noteapp.ui.RecyclerViewEmptyErrorSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * HomeActivity is the activity that will launched after splash screen and also will contains all the logic of
 * showing all the user notes.
 */

public class HomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.activity_home_srl)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.activity_home_rv)
    RecyclerViewEmptyErrorSupport recyclerView;
    @BindView(R.id.activity_home_empty_view)
    View emptyErrorView;
    @BindView(R.id.empty_error_text_tv)
    TextView errorTextView;

    private Unbinder unbinder;

    private NoteAdapter adapter;
    private List<Note> notesList = new ArrayList<>();

    private HomeViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);

        initRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(this);

        observeViewModel();
    }

    @SuppressWarnings("ConstantConditions")
    private void observeViewModel() {
        NoteViewModelFactory viewModelFactory = new NoteViewModelFactory(GetNotesUseCase.getInstance());
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        viewModel.isLoading().observe(this, isLoading -> swipeRefreshLayout.setRefreshing(isLoading));
        viewModel.getError().observe(this, this::buildErrorEmptyView);
        viewModel.getNotes().observe(this, noteList -> adapter.setNoteList(noteList));
    }

    private void buildErrorEmptyView(String error) {
        errorTextView.setText(error);
    }

    private void initRecyclerView() {
        adapter = new NoteAdapter(notesList);
        recyclerView.setEmptyErrorView(emptyErrorView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRefresh() {
        if (viewModel != null)
            viewModel.fetchNotes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
