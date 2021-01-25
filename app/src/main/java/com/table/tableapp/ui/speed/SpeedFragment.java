package com.table.tableapp.ui.speed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.table.tableapp.R;

public class SpeedFragment extends Fragment {

    private SpeedViewModel speedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        speedViewModel =
                ViewModelProviders.of(this).get(SpeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_speed, container, false);
        final TextView textView = root.findViewById(R.id.text_speed);
        speedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}