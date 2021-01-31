package com.table.tableapp.ui.colors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.table.tableapp.R;
import com.table.tableapp.connection.PathRequest;

public class Tab3Fragment extends ColorFragment {

    private View root;
    private PathRequest pr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_color_three, container, false);
        pr = new PathRequest(root.getContext());

        createDeleteColorButtons();

        return root;
    }

    private void createDeleteColorButtons() {
        RelativeLayout layout = root.findViewById(R.id.delete_button_holder);
        pr.createColorButtons("getColorsArray", new BasicCreateColorButton() {
            @Override
            public void setButtonCustomizations(Button button) {
                button.setText("Delete Color: " + (button.getId() - 1));
                button.setOnClickListener(view -> {
                    pr.makeStringRequest("color?del=true&id=" + (button.getId() - 1));
                    System.out.println("Deleting color with id: " + (button.getId() - 1));

                    ((ViewGroup) button.getParent()).removeView(button);
                    int newId = button.getId() + 1;
                    View newButton = layout.findViewById(newId);
                    while (newButton == null && newId <= 64) {
                        newId++;
                        newButton = layout.findViewById(newId);
                    }
                    if (newButton instanceof Button) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) newButton.getLayoutParams();
                        layoutParams.removeRule(RelativeLayout.BELOW);
                        layoutParams.addRule(RelativeLayout.BELOW, ((RelativeLayout.LayoutParams)button.getLayoutParams()).getRules()[RelativeLayout.BELOW]);
                    }
                });
                layout.addView(button);
            }
        });
    }
}