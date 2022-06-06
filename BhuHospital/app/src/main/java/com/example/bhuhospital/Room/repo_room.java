package com.example.bhuhospital.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class repo_room {

    private DaoInterface daoInterface;
    private LiveData<List<appoinment_entity>> dataset;
    private LiveData<List<appoinment_entity>> pending;
    private LiveData<List<appoinment_entity>> non_pending;

    public repo_room(Application application)
    {
        room_database database = room_database.getInstance(application);
        this.daoInterface = database.daoInterface();
        this.dataset = daoInterface.getAllNotes();
        this.pending = daoInterface.getAllPendingNotes();
        this.non_pending = daoInterface.getAllNonPendingNotes();
    }

    public void Insert(appoinment_entity note)
    {
        InsertAsyncTask i = (InsertAsyncTask) new InsertAsyncTask(daoInterface).execute(note);
    }
    public void Update(appoinment_entity note)
    {
        UpdateAsyncTask i = (UpdateAsyncTask) new UpdateAsyncTask(daoInterface).execute(note);
    }
    public void Delete(appoinment_entity note)
    {
        DeleteAsyncTask i = (DeleteAsyncTask) new DeleteAsyncTask(daoInterface).execute(note);
    }
    public void DeleteAllNotes()
    {
        DeleteAllAsyncTask i = (DeleteAllAsyncTask) new DeleteAllAsyncTask(daoInterface).execute();
    }
    public LiveData<List<appoinment_entity>> getAllNonPendingNotes()
    {
        return non_pending;
    }
    public LiveData<List<appoinment_entity>> getAllPendingNotes()
    {
        return pending;
    }
    public LiveData<List<appoinment_entity>> getAllNotes()
    {
        return dataset;
    }





//async methods

    public static class InsertAsyncTask extends AsyncTask<appoinment_entity, Void, Void> {

        DaoInterface noteDao;
        public InsertAsyncTask(DaoInterface noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(appoinment_entity... notes) {
            noteDao.Insert(notes[0]);
            return null;

        }
    };

    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        DaoInterface noteDao;
        public DeleteAllAsyncTask(DaoInterface noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.DeleteAllNotes();
            return null;

        }
    };




    public static class DeleteAsyncTask extends AsyncTask<appoinment_entity, Void, Void> {

        DaoInterface noteDao;
        public DeleteAsyncTask(DaoInterface noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(appoinment_entity... notes) {
            noteDao.Delete(notes[0]);
            return null;

        }
    };


    public static class UpdateAsyncTask extends AsyncTask<appoinment_entity, Void, Void> {

        DaoInterface noteDao;
        public UpdateAsyncTask(DaoInterface noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(appoinment_entity... notes) {
            noteDao.Update(notes[0]);
            return null;

        }
    };

}
