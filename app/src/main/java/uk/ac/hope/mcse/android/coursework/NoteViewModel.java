package uk.ac.hope.mcse.android.coursework;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final MutableLiveData<List<String>> notes = new MutableLiveData<>();
    private final SharedPreferences prefs;
    private final Gson gson = new Gson();
    private static final String PREF_KEY = "saved_notes";

    public NoteViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences("notes_app", Application.MODE_PRIVATE);
        loadNotes();  // Load notes from SharedPreferences when ViewModel is created
    }

    public LiveData<List<String>> getNotes() {
        return notes;
    }

    // Add a new note and save to SharedPreferences
    public void addNote(String note) {
        List<String> current = notes.getValue() != null ? new ArrayList<>(notes.getValue()) : new ArrayList<>();
        current.add(note);
        notes.setValue(current);
        saveNotes(current);
    }

    // Remove a note and save updated list to SharedPreferences
    public void removeNote(String note) {
        List<String> current = notes.getValue() != null ? new ArrayList<>(notes.getValue()) : new ArrayList<>();
        current.remove(note);
        notes.setValue(current);
        saveNotes(current);
    }

    // Update a note and save updated list to SharedPreferences
    public void updateNote(String oldNote, String newNote) {
        List<String> current = notes.getValue() != null ? new ArrayList<>(notes.getValue()) : new ArrayList<>();
        int index = current.indexOf(oldNote);
        if (index != -1) {
            current.set(index, newNote);
            notes.setValue(current);
            saveNotes(current);
        }
    }

    // Save notes to SharedPreferences as JSON string
    private void saveNotes(List<String> noteList) {
        String json = gson.toJson(noteList);
        prefs.edit().putString(PREF_KEY, json).apply();
    }

    // Load notes from SharedPreferences if available
    private void loadNotes() {
        String json = prefs.getString(PREF_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<String>>() {}.getType();
            List<String> loadedNotes = gson.fromJson(json, type);
            notes.setValue(loadedNotes != null ? loadedNotes : new ArrayList<>());
        } else {
            notes.setValue(new ArrayList<>());
        }
    }
}
