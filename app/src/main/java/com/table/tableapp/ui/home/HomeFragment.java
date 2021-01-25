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
        pr.setupOnClickStringRequest(root.findViewById(R.id.glitter), "mode?m=glitter");
        pr.setupOnClickStringRequest(root.findViewById(R.id.loop), "mode?m=all");
        pr.setupOnClickStringRequest(root.findViewById(R.id.solid), "mode?m=solid");
        pr.setupOnClickStringRequest(root.findViewById(R.id.rainbow), "mode?m=rainbow");
        pr.setupOnClickStringRequest(root.findViewById(R.id.rain), "mode?m=rain");

        return root;
    }
}