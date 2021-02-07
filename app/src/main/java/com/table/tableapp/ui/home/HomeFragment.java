package com.table.tableapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        PathRequest pr = new PathRequest(root.getContext());
        root.findViewById(R.id.glitter).setOnClickListener(V -> {
            pr.makeStringRequest("mode?m=glitter");
            changeModeToast("glitter");
        });
        root.findViewById(R.id.loop).setOnClickListener(V -> {
            pr.makeStringRequest("mode?m=all");
            changeModeToast("loop");
        });
        root.findViewById(R.id.solid).setOnClickListener(V -> {
            pr.makeStringRequest("mode?m=solid");
            changeModeToast("a solid color");
        });
        root.findViewById(R.id.rainbow).setOnClickListener(V -> {
            pr.makeStringRequest("mode?m=rainbow");
            changeModeToast("rainbow");
        });
        root.findViewById(R.id.rain).setOnClickListener(V -> {
            pr.makeStringRequest("mode?m=rain");
            changeModeToast("rain");
        });
        root.findViewById(R.id.snake).setOnClickListener(V -> {
            pr.makeStringRequest("mode?m=snakeAI");
            changeModeToast("snake");
        });

        return root;
    }

    private void changeModeToast(String newMode) {
        Toast.makeText(root.getContext(), "Changing mode to " + newMode, Toast.LENGTH_SHORT).show();
    }
}