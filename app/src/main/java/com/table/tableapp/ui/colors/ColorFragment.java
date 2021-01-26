package com.table.tableapp.ui.colors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.table.tableapp.R;

public class ColorFragment extends Fragment {

    private ColorViewModel colorViewModel;

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        colorViewModel =
                ViewModelProviders.of(this).get(ColorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_color, container, false);
        final TextView textView = root.findViewById(R.id.text_color);
        colorViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        viewPager = root.findViewById(R.id.colorViewPager);
        tabLayout = root.findViewById(R.id.colorTabLayout);
        adapter = new TabAdapter(getFragmentManager());

        adapter.addFragment(new Tab1Fragment(), "Tab 1");
        adapter.addFragment(new Tab2Fragment(), "Tab 2");
        adapter.addFragment(new Tab3Fragment(), "Tab 3");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }
}