package com.table.tableapp.connection;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class PathRequest {
    private RequestQueue queue;
    private static final String host = "http://192.168.2.5/";

    public PathRequest(Context cont) {
        queue = Volley.newRequestQueue(cont);
    }

    public void makeStringRequest(String path) {
        String url = host + path;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            // Do nothing with the response :)
        }, error -> {
            // Can't connect to table!
        });
        queue.add(stringRequest);
    }

    public void setupOnClickStringRequest(View V, String path) {
        String url = host + path;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {},
                error -> {});
        V.setOnClickListener(view -> queue.add(stringRequest));
    }
}
