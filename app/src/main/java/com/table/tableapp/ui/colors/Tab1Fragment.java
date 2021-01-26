package com.table.tableapp.ui.colors;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;

public class Tab1Fragment extends Fragment {

    private PathRequest pr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_one, container, false);

        pr = new PathRequest(root.getContext());
        final ColorPickerDialog.Builder[] builder = {createBuilder(root.getContext())};

        Button addColor = root.findViewById(R.id.addColorButton);
        addColor.setOnClickListener((V) -> {
            builder[0].show();
            builder[0] = createBuilder(root.getContext());
        });

        return root;
    }

    private void setLayoutColor(ColorEnvelope envelope) {
        int[] argb = envelope.getArgb();
        pr.makeStringRequest("color?new=true&r=" + argb[1] + "&g=" + argb[2] + "&b=" + argb[3]);
    }

    private ColorPickerDialog.Builder createBuilder(Context context) {
        return new ColorPickerDialog.Builder(context)
                .setTitle(R.string.colorPickerTitle)
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton(getString(R.string.confirm),
                        (ColorEnvelopeListener) (envelope, fromUser) -> setLayoutColor(envelope))
                .setNegativeButton(getString(R.string.cancel),
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(false) // the default value is true.
                .attachBrightnessSlideBar(true)  // the default value is true.
                .setBottomSpace(12); // set a bottom space between the last slidebar and buttons.
    }
}