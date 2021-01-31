package com.table.tableapp.connection;

import android.widget.Button;

public interface CreateButtonWrapper {
    void setButtonCustomizations(Button button);
    int getLastId();
    void setLastId(int id);
    void setId(int id);
}
