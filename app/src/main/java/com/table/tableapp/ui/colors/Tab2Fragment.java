package com.table.tableapp.ui.colors;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.flag.BubbleFlag;
import com.skydoves.colorpickerview.flag.FlagMode;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab2Fragment extends ColorFragment {

    View root;
    int lastId = R.id.hue_light;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_two, container, false);

        RequestQueue queue = Volley.newRequestQueue(root.getContext());

        String url = PathRequest.host + "getColorsArray";
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject o = response.getJSONObject(i);
                    int id = o.getInt("id");
                    int r = o.getInt("r");
                    int g = o.getInt("g");
                    int b = o.getInt("b");
                    int color = Color.rgb(r, g, b);
                    createColorEditButton(id, color);
                } catch (JSONException e) {
                    System.out.println("Json parsing error!");
                }
            }
        }, error -> {
            // Can't connect to table! Might add a snackbar
        });
        queue.add(stringRequest);
        return root;
    }

    private void createColorEditButton(int id, int color) {
        RelativeLayout layout = root.findViewById(R.id.edit_button_holder);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.BELOW, lastId);
        int left = (int)(getResources().getDimension(R.dimen.button_side_margin));
        int top =  (int)(getResources().getDimension(R.dimen.button_top_margin));
        int right = (int)(getResources().getDimension(R.dimen.button_side_margin));
        layoutParams.setMargins(left, top, right, 0);
        Button button = new Button(root.getContext());
        button.setId(id + 1);
        button.setText("Color: " + id);
        button.setLayoutParams(layoutParams);
        button.setBackgroundColor(color);
        layout.addView(button, layoutParams);
        button.setOnClickListener(view -> {
            createBuilder(root.getContext(), id, button).show(); // shows the dialog
        });
        lastId = button.getId();
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
}
