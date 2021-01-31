package com.table.tableapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        PathRequest pr = new PathRequest(root.getContext());
        root.findViewById(R.id.glitter).setOnClickListener(V -> pr.makeStringRequest("mode?m=glitter"));
        root.findViewById(R.id.loop).setOnClickListener(V -> pr.makeStringRequest("mode?m=all"));
        root.findViewById(R.id.solid).setOnClickListener(V -> pr.makeStringRequest("mode?m=solid"));
        root.findViewById(R.id.rainbow).setOnClickListener(V -> pr.makeStringRequest("mode?m=rainbow"));
        root.findViewById(R.id.rain).setOnClickListener(V -> pr.makeStringRequest("mode?m=rain"));
        root.findViewById(R.id.rain).setOnClickListener(V -> pr.makeStringRequest("mode?m=snakeAI"));

        return root;
    }
}