package com.table.tableapp.ui.colors;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.table.tableapp.R;

public class Tab1Fragment extends ColorFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_one, container, false);
        Button addColor = root.findViewById(R.id.addColorButton);
        addColor.setOnClickListener((V) -> createBuilder(root.getContext()).show());

        return root;
    }

    private void setLayoutColor(ColorEnvelope envelope) {
        int[] argb = envelope.getArgb();
        pr.makeStringRequest("color?new=true&r=" + argb[1] + "&g=" + argb[2] + "&b=" + argb[3]);
    }

    private ColorPickerDialog.Builder createBuilder(Context context) {
        return createBasicBuilder(context)
                .setPositiveButton(getString(R.string.confirm),
                (ColorEnvelopeListener) (envelope, fromUser) -> setLayoutColor(envelope));
    }
}