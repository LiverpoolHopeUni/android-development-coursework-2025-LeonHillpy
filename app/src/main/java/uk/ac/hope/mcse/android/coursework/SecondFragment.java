package uk.ac.hope.mcse.android.coursework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import uk.ac.hope.mcse.android.coursework.databinding.FragmentSecondBinding;
import uk.ac.hope.mcse.android.coursework.NoteViewModel;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get reference to the ViewModel
        NoteViewModel viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        // Get your EditText and Button
        EditText noteInput = view.findViewById(R.id.noteInput);
        Button saveButton = view.findViewById(R.id.button_second); // or your custom save button

        // When the button is clicked, save the note and go back
        saveButton.setOnClickListener(v -> {
            String note = noteInput.getText().toString();
            viewModel.setNote(note);  // Save to ViewModel

            // Navigate back to FirstFragment
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}