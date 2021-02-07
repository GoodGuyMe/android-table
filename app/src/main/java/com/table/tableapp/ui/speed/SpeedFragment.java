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

import java.util.concurrent.TimeUnit;

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

        AppCompatSeekBar delta_slider = sliders.findViewById(R.id.delta_slider);
        delta_slider.setOnSeekBarChangeListener(
                pr.createSeekBarChangeListener("speed", "delta", new SeekBarWrapper() {
                    @Override
                    public int convert(int value) {
                        return value;
                    }
                    final TextView textView = root.findViewById(R.id.delta_text);
                    @Override
                    public void setText(Integer value) {
                        if (value == null) {
                            textView.setText("Delta");
                        } else {
                            textView.setText("Delta: " + convert(value));
                        }
                    }
                })
        );

        pr.makeJsonRequest("getSpeeds", response -> {
            try {
                freq_slider.setProgress((int)(Math.sqrt(response.getInt("freq")) * Math.sqrt(freq_slider.getMax())));
                {
                    int value = response.getInt("speed");
                    int calculated = (int) (Math.sqrt(Math.abs(value)) * Math.sqrt(speed_slider.getMax()));
                    speed_slider.setProgress(value > 0 ? (speed_slider.getMax() / 2) + calculated : (speed_slider.getMax() / 2) - calculated);
                }
                fps_slider.setProgress(response.getInt("fps") - 1);
                fade_slider.setProgress((int)(Math.sqrt(response.getInt("fade")) * Math.sqrt(freq_slider.getMax())));
                //TODO: delta_slider.setProgress(response.getInt("delta"));
            } catch (JSONException e) {
                // JSON Parsing error
            }
        });

        return root;
    }
}