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

public class Tab3Fragment extends BasicTabFragment {

    private View root;
    private PathRequest pr;
    private RelativeLayout layout;

    @Override
    protected void refreshButtons() {
        if (layout != null) {
            layout.removeAllViewsInLayout();
            createDeleteColorButtons();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_color_three, container, false);
        pr = new PathRequest(root.getContext());
        layout = root.findViewById(R.id.delete_button_holder);

        createDeleteColorButtons();

        return root;
    }

    private void createDeleteColorButtons() {
        pr.createColorButtons(new BasicCreateColorButton() {
            @Override
            public void setButtonCustomizations(Button button) {
                button.setText("Delete Color: " + (button.getId() - 1));
                button.setOnClickListener(view -> {
                    pr.makeConcurrentStringRequest("color?del=true&id=" + (button.getId() - 1), () -> refreshPage(root));
                });
                layout.addView(button);
            }
        });
    }
}