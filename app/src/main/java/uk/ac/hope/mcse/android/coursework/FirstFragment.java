package uk.ac.hope.mcse.android.coursework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentFirstBinding;
import uk.ac.hope.mcse.android.coursework.NoteViewModel;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get reference to the ViewModel
        NoteViewModel viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        // Get reference to the TextView that will display the saved note
        TextView notesText = view.findViewById(R.id.Notes_text);

        // Observe the note LiveData and update the TextView when the note changes
        // Update the TextView with the new note
        viewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            StringBuilder allNotes = new StringBuilder();
            for (String note : notes) {
                allNotes.append("• ").append(note).append("\n\n");
            }
            notesText.setText(allNotes.toString().trim());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}