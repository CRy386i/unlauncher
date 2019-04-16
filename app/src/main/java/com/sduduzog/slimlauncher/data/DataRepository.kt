package com.sduduzog.slimlauncher.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class DataRepository(application: Application) {

    private val db: DataRoomDatabase = DataRoomDatabase.getDatabase(application)!!
    private val appDao: AppDao = db.appDao()
    private val noteDao: NoteDao = db.noteDao()
    private var _apps: LiveData<List<App>> = appDao.apps
    private var _notes: LiveData<List<Note>> = noteDao.notes

    val apps: LiveData<List<App>>
        get() = _apps

    val notes: LiveData<List<Note>>
        get() = _notes

    fun saveNote(note: Note) {
        SaveNoteAsyncTask(noteDao).execute(note)
    }

    fun updateNote(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteNote(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    private class SaveNoteAsyncTask internal constructor(private val mAsyncTaskDao: NoteDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            val note = params[0]
            mAsyncTaskDao.saveNote(note)
            return null
        }
    }

    private class UpdateNoteAsyncTask internal constructor(private val mAsyncTaskDao: NoteDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            val note = params[0]
            mAsyncTaskDao.updateNote(note)
            return null
        }
    }

    private class DeleteNoteAsyncTask internal constructor(private val mAsyncTaskDao: NoteDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            val note = params[0]
            mAsyncTaskDao.deleteNote(note)
            return null
        }
    }


    companion object {

        @Volatile
        @JvmStatic
        private var INSTANCE: DataRepository? = null

        fun getInstance(application: Application): DataRepository {
            synchronized(DataRepository::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = DataRepository(application)
                }
                return INSTANCE!!
            }
        }
    }
}