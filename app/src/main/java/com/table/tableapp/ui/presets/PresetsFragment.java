package com.table.tableapp.ui.presets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.table.tableapp.R;
import com.table.tableapp.connection.CreateButtonWrapper;
import com.table.tableapp.connection.PathRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PresetsFragment extends Fragment {

    private View root;
    private PathRequest pr;
    private File presets;

    private static final int COLUMN_SIZE = 4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_preset, container, false);
        pr = new PathRequest(root.getContext());

        presets = new File(root.getContext().getFilesDir(), "presets.txt");
        createAddPresetButton();
        createCustomPresetButtons();

        return root;
    }

    private JSONArray getPresetFromFile() throws IOException, JSONException {
        FileInputStream fIn = new FileInputStream(presets);
        Scanner sc = new Scanner(fIn);
        StringBuilder stringBuilder = new StringBuilder();
        while (sc.hasNextLine()) {
            stringBuilder.append(sc.nextLine());
        }
        sc.close();
        fIn.close();
        return new JSONArray(stringBuilder.toString());
    }

    private void createAddPresetButton() {
        View button = root.findViewById(R.id.add_preset_button);
        button.setOnClickListener(view -> {
            pr.makeJsonRequest("getCurrentPreset", response -> {
                // Create alert dialog to show a text field.
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setTitle("Set a name");
                final EditText input = new EditText(root.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    try {
                        String title = input.getText().toString();
                        response.put("title", title);
                        //TODO: Let user pick button color
                        JSONArray jsonArr;
                        try {
                            jsonArr = getPresetFromFile();
                        } catch (FileNotFoundException | JSONException e) {
                            jsonArr = new JSONArray();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }

                        jsonArr.put(response);
                        FileOutputStream fOut = new FileOutputStream(presets);
                        fOut.write(jsonArr.toString().getBytes(StandardCharsets.UTF_8));
                        fOut.close();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
                builder.show();
            });
        });
    }

    private void createCustomPresetButtons() {
        RelativeLayout layout = root.findViewById(R.id.preset_button_holder);
        try {
            JSONArray jsonArr = getPresetFromFile();
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject preset = jsonArr.getJSONObject(i);
                pr.createBasicButton(i + 1, new CreateButtonWrapper() {
                    @Override
                    public void setButtonCustomizations(Button button) {
                        try {
                            button.setText(preset.getString("title"));
                            button.setOnClickListener(view -> {
                                try {
                                    String mode = preset.getString("mode");
                                    pr.makeStringRequest("mode?m=" + mode);

                                    JSONArray colors = preset.getJSONArray("colors");
                                    pr.setNewColors(() -> {
                                        try {
                                            for (int i = 0; i < colors.length(); i++) {
                                                JSONObject o = colors.getJSONObject(i);
                                                int r = o.getInt("r");
                                                int g = o.getInt("g");
                                                int b = o.getInt("b");
                                                pr.makeStringRequest("color?new=true&r=" + r + "&g=" + g + "&b=" + b);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    });

                                    int freq = preset.getInt("freq");
                                    int speed = preset.getInt("speed");
                                    int fade = preset.getInt("fade");
                                    int fps = preset.getInt("fps");
                                    int delta = preset.getInt("delta");

                                    pr.makeStringRequest("speed?freq=" + freq + "&speed=" + speed + "&fade=" + fade + "&fps=" + fps + "&delta=" + delta);

                                    int brightness = preset.getInt("brightness");
                                    pr.makeStringRequest("brightness?b=" + brightness);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                            button.setOnLongClickListener(view -> {
                                jsonArr.remove(button.getId() - 1);
                                try {
                                    FileOutputStream fOut = new FileOutputStream(presets);
                                    fOut.write(jsonArr.toString().getBytes(StandardCharsets.UTF_8));
                                    fOut.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ((ViewGroup) button.getParent()).removeView(button);
                                return true;
                            });
                            layout.addView(button);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void setLayoutParams(Button button) {
                        int id = button.getId();
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                (int)(button.getContext().getResources().getDimension(R.dimen.square_button_size)),
                                (int)(button.getContext().getResources().getDimension(R.dimen.square_button_size))
                        );
                        int left, right, top;
                        if (id == 1) {
                            layoutParams.addRule(RelativeLayout.BELOW, R.id.preset_button_holder);
                        }
                        else if (id <= COLUMN_SIZE) {
                            layoutParams.addRule(RelativeLayout.BELOW, id - 1);
                        } else if (id == COLUMN_SIZE + 1) {
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, 1);
                            layoutParams.addRule(RelativeLayout.BELOW, R.id.preset_button_holder);
                        } else {
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, id - COLUMN_SIZE);
                            layoutParams.addRule(RelativeLayout.BELOW, id - 1);
                        }

                        left = (int)(button.getContext().getResources().getDimension(R.dimen.button_side_margin));
                        top =  (int)(button.getContext().getResources().getDimension(R.dimen.button_top_margin));
                        right = (int)(button.getContext().getResources().getDimension(R.dimen.button_side_margin));

                        layoutParams.setMargins(left, top, right, 0);
                        button.setLayoutParams(layoutParams);
                    }
                });
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
