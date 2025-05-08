package uk.ac.hope.mcse.android.coursework;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ImageView;

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

                // Delete Button
                ImageButton deleteButton = new ImageButton(requireContext());
                deleteButton.setImageResource(R.drawable.ic_delete); // bin icon
                deleteButton.setBackgroundResource(R.drawable.red_rounded_button); // red rounded background
                deleteButton.setColorFilter(Color.WHITE); // make icon white
                deleteButton.setScaleType(ImageView.ScaleType.FIT_CENTER);

                // Reduce padding so icon appears larger inside button
                int paddingInDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
                deleteButton.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp);

                // Set fixed dp size for button
                int sizeInDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                        sizeInDp, sizeInDp
                );
                btnParams.gravity = Gravity.END;
                btnParams.topMargin = 16;
                deleteButton.setLayoutParams(btnParams);

                deleteButton.setOnClickListener(v -> viewModel.removeNote(note));

                // Add views to block
                noteBlock.addView(noteText);
                noteBlock.addView(deleteButton);

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
