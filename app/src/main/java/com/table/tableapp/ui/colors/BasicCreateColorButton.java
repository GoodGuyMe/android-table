package com.table.tableapp.ui.colors;

import android.widget.Button;
import com.table.tableapp.connection.CreateButtonWrapper;

public abstract class BasicCreateColorButton implements CreateButtonWrapper {
    public abstract void setButtonCustomizations(Button button);

    private int lastId;
    protected int id;

    @Override
    public int getLastId() {
        return lastId;
    }

    @Override
    public void setLastId(int id) {
        this.lastId = id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
