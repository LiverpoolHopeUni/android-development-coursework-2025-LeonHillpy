package uk.ac.hope.mcse.android.coursework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoteViewModel extends ViewModel {
    private final MutableLiveData<String> note = new MutableLiveData<>();

    public void setNote(String newNote) {
        note.setValue(newNote);
    }

    public LiveData<String> getNote() {
        return note;
    }
}