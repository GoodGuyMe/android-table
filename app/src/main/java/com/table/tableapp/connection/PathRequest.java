package com.table.tableapp.connection;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.table.tableapp.R;
import org.json.JSONException;
import org.json.JSONObject;

public class PathRequest {
    private final RequestQueue queue;
    private Context cont;
    public static final String host = "http://192.168.2.5/";

    // minimum delay between request
    private static final long delay = 20;
    private long time = System.currentTimeMillis();

    public PathRequest(Context cont) {
        this.cont = cont;
        queue = Volley.newRequestQueue(cont);
    }

    public void makeStringRequest(String path) {
        String url = host + path;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, null, error -> {
            // Can't connect to table! // TODO: Add Snackbar
        });
        queue.add(stringRequest);
    }

    public void makeJsonRequest(String path, JsonRequestWrapper wrapper) {
        String url = host + path;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, wrapper::setResponse, error -> {
            // Can't connect to table! // TODO: Add Snackbar
        });
        queue.add(jsonRequest);
    }

    public void makeJsonArrayRequest(String path, JsonArrayRequestWrapper wrapper) {
        String url = host + path;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, wrapper::setResponse, error -> {
            // Can't connect to table! // TODO: Add Snackbar
        });
        queue.add(jsonArrayRequest);
    }

    public AppCompatSeekBar.OnSeekBarChangeListener createSeekBarChangeListener(String path, String param, SeekBarWrapper wrapper) {
        return new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser){
                if (System.currentTimeMillis() >= time + delay) {
                    time = System.currentTimeMillis();
                    makeStringRequest(path + "?" + param + "=" + wrapper.convert(progress));
                    wrapper.setText(progress);
                }
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar){
            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar){
                makeStringRequest(path + "?" + param + "=" + wrapper.convert(seekBar.getProgress()));
                wrapper.setText(null);
            }
        };
    }

    public void createColorButtons(String path, CreateButtonWrapper wrapper) {
        makeJsonArrayRequest(path, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject o = response.getJSONObject(i);
                    int id = o.getInt("id");
                    int r = o.getInt("r");
                    int g = o.getInt("g");
                    int b = o.getInt("b");
                    int color = Color.rgb(r, g, b);
                    createBasicButton(id, color, wrapper);
                } catch (JSONException e) {
                    System.out.println("Json parsing error!");
                }
            }
        });
    }

    public void createBasicButton(int id, int color, CreateButtonWrapper wrapper) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.BELOW, wrapper.getLastId());
        int left = (int)(cont.getResources().getDimension(R.dimen.button_side_margin));
        int top =  (int)(cont.getResources().getDimension(R.dimen.button_top_margin));
        int right = (int)(cont.getResources().getDimension(R.dimen.button_side_margin));
        layoutParams.setMargins(left, top, right, 0);
        Button button = new Button(cont);
        button.setId(id + 1);
        button.setLayoutParams(layoutParams);
        button.setBackgroundColor(color);
        wrapper.setId(id);
        wrapper.setLastId(button.getId());
        wrapper.setButtonCustomizations(button);
    }
}
