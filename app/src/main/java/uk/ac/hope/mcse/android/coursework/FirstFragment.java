package uk.ac.hope.mcse.android.coursework;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentFirstBinding;

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

        // Get the container layout where we will add note blocks
        LinearLayout notesContainer = view.findViewById(R.id.notes_container);

        // Observe the notes LiveData
        viewModel.getNotes().observe(getViewLifecycleOwner(), noteList -> {
            // Clear previous views
            notesContainer.removeAllViews();

            // Add each note as a block with a delete button
            for (String note : noteList) {
                // Create a horizontal layout for the note + delete button
                LinearLayout noteRow = new LinearLayout(requireContext());
                noteRow.setOrientation(LinearLayout.HORIZONTAL);
                noteRow.setPadding(8, 8, 8, 8);
                noteRow.setBackgroundResource(R.drawable.note_background);

                LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                rowParams.setMargins(0, 0, 0, 24);
                noteRow.setLayoutParams(rowParams);

                // Create the note TextView
                TextView noteText = new TextView(requireContext());
                noteText.setText(note);
                noteText.setTextSize(16);
                noteText.setTextColor(Color.WHITE);
                noteText.setLayoutParams(new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
                ));

                // Create the delete button
                Button deleteButton = new Button(requireContext());
                deleteButton.setText(getString(R.string.delete_button_text));
                deleteButton.setOnClickListener(v -> viewModel.removeNote(note));

                // Add TextView and Button to the row
                noteRow.addView(noteText);
                noteRow.addView(deleteButton);

                // Add the row to the container
                notesContainer.addView(noteRow);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
