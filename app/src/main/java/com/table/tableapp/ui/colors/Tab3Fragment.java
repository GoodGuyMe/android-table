package com.table.tableapp.ui.colors;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab3Fragment extends ColorFragment {

    private View root;
    int lastId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_three, container, false);
        lastId = R.id.delete_button_holder;

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
                    removeButton(id, color);
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

    private void removeButton(int id, int color) {
        RelativeLayout layout = root.findViewById(R.id.delete_button_holder);

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
        button.setText("Delete Color: " + id);
        button.setLayoutParams(layoutParams);
        button.setBackgroundColor(color);
        layout.addView(button, layoutParams);
        button.setOnClickListener(view -> {
            pr.makeStringRequest("color?del=true&id=" + id);
            System.out.println("Deleting color with id: " + id);
            ((ViewGroup) button.getParent()).removeView(button);
            int newId = button.getId() - 1;
            View newButton = layout.findViewById(newId);
            while (newButton == null && newId > 0) {
                newId--;
                newButton = layout.findViewById(newId);
            }
            if (newButton != null) {
                newButton.setId(button.getId());
            }
        });
        lastId = button.getId();
    }
}