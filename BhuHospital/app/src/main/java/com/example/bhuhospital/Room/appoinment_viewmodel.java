package com.example.bhuhospital.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class appoinment_viewmodel extends AndroidViewModel {

    private repo_room repo;
    private LiveData<List<appoinment_entity>>dataset,pending,non_pending;
    public appoinment_viewmodel(@NonNull Application application) {
        super(application);
        repo = new repo_room(application);
        dataset = repo.getAllNotes();
        non_pending = repo.getAllNonPendingNotes();
        pending = repo.getAllPendingNotes();

    }


    public void insert(appoinment_entity note)
    {
        repo.Insert(note);
    }

    public void update(appoinment_entity note)
    {
        repo.Update(note);
    }

    public void delete(appoinment_entity note)
    {
        repo.Delete(note);
    }

    public void deleteAll()
    {
        repo.DeleteAllNotes();
    }

    public LiveData<List<appoinment_entity>> getAllNotes(){return dataset;}

    public LiveData<List<appoinment_entity>> getAllPendingNotes(){return pending;}

    public LiveData<List<appoinment_entity>> getAllNonPendingNotes(){return non_pending;}

}
