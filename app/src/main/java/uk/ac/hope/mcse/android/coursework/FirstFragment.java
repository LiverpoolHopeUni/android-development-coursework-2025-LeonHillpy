package uk.ac.hope.mcse.android.coursework;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

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

        NoteViewModel viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        LinearLayout notesContainer = view.findViewById(R.id.notes_container);

        viewModel.getNotes().observe(getViewLifecycleOwner(), noteList -> {
            notesContainer.removeAllViews();

            for (String note : noteList) {

                // Outer vertical block
                LinearLayout noteBlock = new LinearLayout(requireContext());
                noteBlock.setOrientation(LinearLayout.VERTICAL);
                noteBlock.setPadding(24, 24, 24, 24);
                noteBlock.setBackgroundResource(R.drawable.note_background);

                LinearLayout.LayoutParams blockParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                blockParams.setMargins(0, 0, 0, 32);
                noteBlock.setLayoutParams(blockParams);

                // Note Text
                TextView noteText = new TextView(requireContext());
                noteText.setText(note);
                noteText.setTextSize(16);
                noteText.setTextColor(Color.BLACK);
                noteText.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                // Button common size/padding
                int paddingInDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
                int sizeInDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());

                // Delete Button
                ImageButton deleteButton = new ImageButton(requireContext());
                deleteButton.setImageResource(R.drawable.ic_delete); // bin icon
                deleteButton.setBackgroundResource(R.drawable.red_rounded_button);
                deleteButton.setColorFilter(Color.WHITE);
                deleteButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                deleteButton.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp);
                LinearLayout.LayoutParams deleteBtnParams = new LinearLayout.LayoutParams(sizeInDp, sizeInDp);
                deleteBtnParams.setMargins(16, 16, 0, 0);
                deleteButton.setLayoutParams(deleteBtnParams);

                deleteButton.setOnClickListener(v -> viewModel.removeNote(note));

                // Edit Button
                ImageButton editButton = new ImageButton(requireContext());
                editButton.setImageResource(R.drawable.ic_edit); // pencil icon
                editButton.setBackgroundResource(R.drawable.blue_rounded_button);
                editButton.setColorFilter(Color.WHITE);
                editButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                editButton.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp);
                LinearLayout.LayoutParams editBtnParams = new LinearLayout.LayoutParams(sizeInDp, sizeInDp);
                editBtnParams.setMargins(0, 16, 0, 0);
                editButton.setLayoutParams(editBtnParams);

                editButton.setOnClickListener(v -> {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
                    builder.setTitle("Edit Note");

                    final EditText input = new EditText(requireContext());
                    input.setText(note);
                    builder.setView(input);

                    builder.setPositiveButton("Save", (dialog, which) -> {
                        String editedNote = input.getText().toString().trim();
                        if (!editedNote.isEmpty() && !editedNote.equals(note)) {
                            viewModel.updateNote(note, editedNote);
                        }
                    });

                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                    builder.show();
                });

                // Horizontal layout for buttons
                LinearLayout buttonRow = new LinearLayout(requireContext());
                buttonRow.setOrientation(LinearLayout.HORIZONTAL);
                buttonRow.setGravity(Gravity.END);
                buttonRow.addView(editButton);
                buttonRow.addView(deleteButton);

                // Add views to block
                noteBlock.addView(noteText);
                noteBlock.addView(buttonRow);

                // Add block to container
                notesContainer.addView(noteBlock);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
