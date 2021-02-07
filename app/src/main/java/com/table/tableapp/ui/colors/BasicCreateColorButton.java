package com.table.tableapp.ui.colors;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.table.tableapp.R;
import com.table.tableapp.connection.CreateButtonWrapper;

public abstract class BasicCreateColorButton implements CreateButtonWrapper {
    public abstract void setButtonCustomizations(Button button);

    private int lastId = 0;

    @Override
    public void setLayoutParams(Button button) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.BELOW, lastId);

        int left = (int)(button.getContext().getResources().getDimension(R.dimen.button_side_margin));
        int top =  (int)(button.getContext().getResources().getDimension(R.dimen.button_top_margin));
        int right = (int)(button.getContext().getResources().getDimension(R.dimen.button_side_margin));

        layoutParams.setMargins(left, top, right, 0);
        button.setLayoutParams(layoutParams);

        lastId = button.getId();
    }
}
