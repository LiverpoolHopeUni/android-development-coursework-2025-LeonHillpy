package uk.ac.hope.mcse.android.coursework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends ViewModel {

    private final MutableLiveData<List<String>> notes = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getNotes() {
        return notes;
    }

    public void addNote(String note) {
        List<String> currentNotes = notes.getValue();
        if (currentNotes != null) {
            currentNotes.add(note);
            notes.setValue(new ArrayList<>(currentNotes)); // trigger LiveData update
        }
    }

    public void removeNote(String note) {
        List<String> currentNotes = notes.getValue();
        if (currentNotes != null) {
            currentNotes.remove(note);
            notes.setValue(new ArrayList<>(currentNotes)); // trigger LiveData update
        }
    }

    public void updateNote(String oldNote, String newNote) {
        List<String> currentNotes = notes.getValue();
        if (currentNotes != null) {
            int index = currentNotes.indexOf(oldNote);
            if (index != -1) {
                currentNotes.set(index, newNote);
                notes.setValue(new ArrayList<>(currentNotes)); // trigger LiveData update
            }
        }
    }
}
