package com.table.tableapp.ui.colors;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.flag.BubbleFlag;
import com.skydoves.colorpickerview.flag.FlagMode;
import com.table.tableapp.R;

public abstract class BasicTabFragment extends Fragment {
    View root;
    protected abstract void refreshButtons();

    protected void refreshPage(View root) {
        TabAdapter adapter = (TabAdapter) ((ViewPager)root.getParent()).getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            ((BasicTabFragment)adapter.getItem(i)).refreshButtons();
        }
    }

    public ColorPickerDialog.Builder createBasicBuilder(Context context) {
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogTheme))
                .setTitle(R.string.colorPickerTitle)
                .setPreferenceName("MyColorPickerDialog")
                .setNegativeButton(getString(R.string.cancel),
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(false) // the default value is true.
                .attachBrightnessSlideBar(true)  // the default value is true.
                .setBottomSpace(12); // set a bottom space between the last slide bar and buttons.

        ColorPickerView colorPickerView = builder.getColorPickerView();
        BubbleFlag bubbleFlag = new BubbleFlag(context);
        bubbleFlag.setFlagMode(FlagMode.FADE);
        colorPickerView.setFlagView(bubbleFlag);
        return builder;
    }
}
