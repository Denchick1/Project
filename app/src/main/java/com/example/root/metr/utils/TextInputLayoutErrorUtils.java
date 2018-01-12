package com.example.root.metr.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

public class TextInputLayoutErrorUtils {

    private static void setFalseTextListner(TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public static void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        setFalseTextListner(textInputLayout);
    }
}
