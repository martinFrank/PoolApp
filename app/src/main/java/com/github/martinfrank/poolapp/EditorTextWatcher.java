package com.github.martinfrank.poolapp;

import android.text.Editable;
import android.text.TextWatcher;

public class EditorTextWatcher implements TextWatcher {

    private final InputChangeListener inputChangeListener;
    private boolean enabled = true;

    public EditorTextWatcher(InputChangeListener inputChangeListener) {
        this.inputChangeListener = inputChangeListener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(enabled){
            inputChangeListener.inputChanged();
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
