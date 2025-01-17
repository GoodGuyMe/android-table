package com.table.tableapp.ui.colors;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;

public class Tab2Fragment extends BasicTabFragment {

    private PathRequest pr;
    private RelativeLayout layout;

    @Override
    protected void refreshButtons() {
        if (layout != null) {
            layout.removeAllViewsInLayout();
            createColorButtons();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_color_two, container, false);
        pr = new PathRequest(root.getContext());
        layout = root.findViewById(R.id.edit_button_holder);
        createColorButtons();

        return root;
    }

    private void createColorButtons() {
        pr.createColorButtons(new BasicCreateColorButton() {
            @Override
            public void setButtonCustomizations(Button button) {
                button.setText("Color: " + (button.getId() - 1));
                button.setOnClickListener(view -> {
                    createBuilder(root.getContext(), (button.getId() - 1)).show(); // shows the dialog
                });
                layout.addView(button, button.getLayoutParams());
            }
        });
    }

    private void setLayoutColor(ColorEnvelope envelope, int id) {
        int[] argb = envelope.getArgb();
        pr.makeConcurrentStringRequest("color?id=" + id + "&r=" + argb[1] + "&g=" + argb[2] + "&b=" + argb[3], () -> refreshPage(root));
    }

    private ColorPickerDialog.Builder createBuilder(Context context, int id) {
        return createBasicBuilder(context)
                .setPositiveButton(getString(R.string.confirm),
                        (ColorEnvelopeListener) (envelope, fromUser) -> setLayoutColor(envelope, id));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
