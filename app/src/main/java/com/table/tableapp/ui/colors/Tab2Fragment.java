package com.table.tableapp.ui.colors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Tab2Fragment extends ColorFragment {

    private View root;
    private PathRequest pr;
    private HashMap<Integer, Integer> colors;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_color_two, container, false);
        pr = new PathRequest(root.getContext());
        colors = new HashMap<>();
        createColorButtons();

        return root;
    }

    private void createColorButtons() {
        RelativeLayout layout = root.findViewById(R.id.edit_button_holder);
        pr.createColorButtons("getColorsArray", new BasicCreateColorButton() {
            @Override
            public void setButtonCustomizations(Button button) {
                colors.put(button.getId() - 1, ((ColorDrawable)button.getBackground()).getColor());
                button.setText("Color: " + (button.getId() - 1));
                button.setOnClickListener(view -> {
                    createBuilder(root.getContext(), (button.getId() - 1), button).show(); // shows the dialog
                });
                layout.addView(button, button.getLayoutParams());
            }
        });
    }

    private void checkAccurateColors() {
        pr.makeJsonArrayRequest("getColorsArray", response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject o = response.getJSONObject(i);
                    int id = o.getInt("id");
                    int r = o.getInt("r");
                    int g = o.getInt("g");
                    int b = o.getInt("b");
                    int color = Color.rgb(r, g, b);
                } catch (JSONException e) {
                    System.out.println("Json parsing error!");
                }
            }
        });
    }

    private void setLayoutColor(ColorEnvelope envelope, int id, View button) {
        int[] argb = envelope.getArgb();
        pr.makeStringRequest("color?id=" + id + "&r=" + argb[1] + "&g=" + argb[2] + "&b=" + argb[3]);
        button.setBackgroundColor(envelope.getColor());
    }

    private ColorPickerDialog.Builder createBuilder(Context context, int id, View button) {
        return createBasicBuilder(context)
                .setPositiveButton(getString(R.string.confirm),
                        (ColorEnvelopeListener) (envelope, fromUser) -> setLayoutColor(envelope, id, button));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
