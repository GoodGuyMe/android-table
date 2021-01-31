package com.table.tableapp.ui.speed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;
import com.table.tableapp.connection.SeekBarWrapper;
import org.json.JSONException;

public class SpeedFragment extends Fragment {

    private SpeedViewModel speedViewModel;
    private PathRequest pr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        speedViewModel =
                ViewModelProviders.of(this).get(SpeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_speed, container, false);
        final TextView textView = root.findViewById(R.id.text_speed);
        speedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        pr = new PathRequest(root.getContext());
        RelativeLayout sliders = root.findViewById(R.id.slider_holder);

        AppCompatSeekBar freq_slider = sliders.findViewById(R.id.freq_slider);
        freq_slider.setOnSeekBarChangeListener(
                pr.createSeekBarChangeListener("speed", "freq", new SeekBarWrapper() {
                    @Override
                    public int convert(int value) {
                        return (int) Math.ceil(value * value / (double)freq_slider.getMax());
                    }
                    final TextView textView = root.findViewById(R.id.freq_text);
                    @Override
                    public void setText(Integer value) {
                        if (value == null) {
                            textView.setText("Frequency");
                        } else {
                            textView.setText("Frequency: " + convert(value));
                        }
                    }
                })
        );

        AppCompatSeekBar speed_slider = sliders.findViewById(R.id.speed_slider);
        speed_slider.setOnSeekBarChangeListener(
                pr.createSeekBarChangeListener("speed", "speed", new SeekBarWrapper() {
                    @Override
                    public int convert(int value) {
                        value -= speed_slider.getMax() / 2;
                        int newValue = value * value / speed_slider.getMax();
                        return value >= 0 ? newValue : newValue * -1;
                    }
                    final TextView textView = root.findViewById(R.id.speed_text);
                    @Override
                    public void setText(Integer value) {
                        if (value == null) {
                            textView.setText("Speed");
                        } else {
                            textView.setText("Speed: " + convert(value));
                        }
                    }
                })
        );

        AppCompatSeekBar fade_slider = sliders.findViewById(R.id.fade_slider);
        fade_slider.setOnSeekBarChangeListener(
                pr.createSeekBarChangeListener("speed", "fade", new SeekBarWrapper() {
                    @Override
                    public int convert(int value) {
                        return (int) Math.ceil(value * value / (double)fade_slider.getMax());
                    }
                    final TextView textView = root.findViewById(R.id.fade_text);
                    @Override
                    public void setText(Integer value) {
                        if (value == null) {
                            textView.setText("Fade");
                        } else {
                            textView.setText("Fade: " + convert(value));
                        }
                    }
                })
        );

        AppCompatSeekBar fps_slider = sliders.findViewById(R.id.fps_slider);
        fps_slider.setOnSeekBarChangeListener(
                pr.createSeekBarChangeListener("speed", "fps", new SeekBarWrapper() {
                    @Override
                    public int convert(int value) {
                        return value + 1;
                    }
                    final TextView textView = root.findViewById(R.id.fps_text);
                    @Override
                    public void setText(Integer value) {
                        if (value == null) {
                            textView.setText("FPS");
                        } else {
                            textView.setText("FPS: " + convert(value));
                        }
                    }
                })
        );

        pr.makeJsonRequest("getSpeeds", response -> {
            try {
                freq_slider.setProgress(response.getInt("freq"));
                speed_slider.setProgress(response.getInt("speed"));
                fps_slider.setProgress(response.getInt("fps"));
                fade_slider.setProgress(response.getInt("fade"));
            } catch (JSONException e) {
                // JSON Parsing error
            }
        });

        return root;
    }
}