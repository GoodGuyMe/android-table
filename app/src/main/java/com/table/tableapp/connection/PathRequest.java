package com.table.tableapp.connection;

import android.content.Context;
import android.graphics.Color;
import android.widget.*;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PathRequest {
    private final RequestQueue queue;
    private final Context cont;
    public static final String host = "http://192.168.2.5/";

    // minimum delay between request
    private static final long delay = 25;
    private long time = System.currentTimeMillis();
    private final ScheduledExecutorService executorService;

    private void connectionError() {
        Toast.makeText(cont, "Error connecting to table.", Toast.LENGTH_LONG).show();
    }

    public PathRequest(Context cont) {
        this.cont = cont;
        queue = Volley.newRequestQueue(cont);
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void makeStringRequest(String path) {
        String url = host + path;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, null, error -> {
            // Can't connect to table!
            connectionError();
        });
        queue.add(stringRequest);
    }

    public void makeConcurrentStringRequest(String path, ConcurrentRequestWrapper wrapper) {
        String url = host + path;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> wrapper.next(), error -> {
            // Can't connect to table!
            connectionError();
        });
        queue.add(stringRequest);
    }

    public void makeJsonRequest(String path, JsonRequestWrapper wrapper) {
        String url = host + path;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, wrapper::setResponse, error -> {
            // Can't connect to table!
            connectionError();
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
                wrapper.setText(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar){
                makeStringRequest(path + "?" + param + "=" + wrapper.convert(seekBar.getProgress()));
                executorService.schedule(() -> setWrapperFinalText(wrapper), 1, TimeUnit.SECONDS);
            }
        };
    }

    private int parseJsonColor(JSONObject o) throws JSONException {
        int r = o.getInt("r");
        int g = o.getInt("g");
        int b = o.getInt("b");
        return Color.rgb(r, g, b);
    }

    private static void setWrapperFinalText(SeekBarWrapper wrapper) {
        wrapper.setText(null);
    }

    public void createColorButtons(CreateButtonWrapper wrapper) {
        makeJsonArrayRequest("getColorsArray", response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject o = response.getJSONObject(i);
                    int id = o.getInt("id");
                    int color = parseJsonColor(o);
                    Button button = createBasicButton(id + 1, wrapper);
                    button.setBackgroundColor(color);
                } catch (JSONException e) {
                    System.out.println("Json parsing error!");
                }
            }
        });
    }

    public Button createBasicButton(int id, CreateButtonWrapper wrapper) {
        Button button = new Button(cont);
        button.setId(id);
        wrapper.setLayoutParams(button);
        wrapper.setButtonCustomizations(button);
        return button;
    }

    public void setNewColors(ConcurrentRequestWrapper last) {
        makeJsonArrayRequest("getColorsArray", response -> {
            makeConcurrentStringRequest("color?del=true&all=true", last);
        });
    }
}
