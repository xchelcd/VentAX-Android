package com.idax.ventax.TextWatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.idax.ventax.Dialog.Enroll.EnrollDialog;

public class EnrollTextWatcher implements TextWatcher {

    private EnrollDialog registerDialog;

    private TextInputLayout textInputLayout;
    private EditText editText;
    private String pass;
    private int identifier;

    public boolean result = false;

    public EnrollTextWatcher(EnrollDialog registerDialog, TextInputLayout textInputLayout, int identifier) {
        this.registerDialog = registerDialog;
        this.textInputLayout = textInputLayout;
        this.editText = textInputLayout.getEditText();
        this.identifier = identifier;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String error = "";
        switch (identifier) {
            case 0:// name
                result = s.toString().length() >= 3;
                error = result ? null : "Escriba un nombre válido";
                EnrollDialog.name = result;
                break;
            case 1://last name
                result = s.toString().length() >= 3;
                error = result ? null : "Escriba un apellido válido";
                EnrollDialog.lastName = result;
                break;
            case 2://phone
                result = s.toString().length() == 10;
                error = result ? null : "Se requiren 10 dígitos";
                EnrollDialog.phone = result;
                break;
            case 3://password
                if (registerDialog.confirmPasswordTextInput.getEditText().getText().toString().isEmpty()) {
                    result = s.toString().length() >= 3;
                    error = result ? null : "Escriba una contraña mayor a 3";
                } else if (registerDialog.confirmPasswordTextInput.getEditText().getText().toString().equals(textInputLayout.getEditText().getText().toString())) {
                    result = true;
                    EnrollDialog.cPass = true;
                    error = null;
                    registerDialog.confirmPasswordTextInput.setError(null);
                } else {
                    result = registerDialog.confirmPasswordTextInput.getEditText().getText().toString().equals(s.toString());
                    error = result ? null : "No coinciden las contraseña";
                }
                EnrollDialog.pass = result;
                EnrollDialog.sPass = s.toString();
                break;
            case 4://confirm password
                if (registerDialog.passwordTextInput.getEditText().getText().toString().equals(textInputLayout.getEditText().getText().toString())) {
                    result = true;
                    EnrollDialog.pass = true;
                    error = null;
                    registerDialog.passwordTextInput.setError(null);
                } else {
                    result = s.toString().equals(EnrollDialog.sPass);
                    error = result ? null : "No coinciden las contraseña";
                }
                EnrollDialog.cPass = result;
                EnrollDialog.sCPass = s.toString();
                break;
        }
        textInputLayout.setError(error);
    }
}
