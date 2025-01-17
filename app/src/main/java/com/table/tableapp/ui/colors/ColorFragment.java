package com.table.tableapp.ui.colors;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.flag.BubbleFlag;
import com.skydoves.colorpickerview.flag.FlagMode;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;

import java.util.ArrayList;

public class ColorFragment extends Fragment {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View root;
    protected PathRequest pr;
    protected static ArrayList<Integer> colors;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_color, container, false);
        colors = new ArrayList<>();

        viewPager = root.findViewById(R.id.colorViewPager);
        tabLayout = root.findViewById(R.id.colorTabLayout);
        adapter = new TabAdapter(getFragmentManager());

        adapter.addFragment(new Tab1Fragment(), "Add");
        adapter.addFragment(new Tab2Fragment(), "Update");
        adapter.addFragment(new Tab3Fragment(), "Delete");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        pr = new PathRequest(root.getContext());

        return root;
    }
}