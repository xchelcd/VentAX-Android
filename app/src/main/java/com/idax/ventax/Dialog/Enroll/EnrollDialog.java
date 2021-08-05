package com.idax.ventax.Dialog.Enroll;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gbksoft.countrycodepickerlib.CountryCodePicker;
import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.idax.ventax.Dialog.InformationDialog;
import com.idax.ventax.Entity.User;
import com.idax.ventax.R;
import com.idax.ventax.TextWatcher.EnrollTextWatcher;

import java.util.concurrent.TimeUnit;

public class EnrollDialog extends DialogFragment implements EnrollView {

    private TextInputLayout nameTextInput;
    private TextInputLayout lastNameTextInput;
    private TextInputLayout phoneNumberTextInput;
    public TextInputLayout passwordTextInput;
    public TextInputLayout confirmPasswordTextInput;
    private CountryCodePicker ccp;
    private Pinview pinview;
    private Button multiActionButton;
    private LinearLayout registerLinearLayout;
    private TextView verifyNumberTextView;

    private boolean flagButton = false;

    private FirebaseAuth mAuth;
    private String verificationId;

    private User user;

    private EnrollPresenter presenter;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_enroll, null, false);
        builder.setView(v);
        inits(v);
        listeners();

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }

    private void listeners() {
        multiActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "" + name + lastName + phone + pass + cPass, Toast.LENGTH_SHORT).show();
                if (flagButton) {
                    if (pinview.getValue().length() != 6) {
                        Toast.makeText(getContext(), "Requiere 6 dígitos el código de verificación", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    signIn();
                } else {
                    if (name && lastName && phone && pass && cPass) {
                        flagButton = true;
                        setViewsEnable(false);
                        Toast.makeText(getContext(), "Espere su código", Toast.LENGTH_LONG).show();
                        modifyViews();
                        user = new User(nameTextInput.getEditText().getText().toString(),
                                lastNameTextInput.getEditText().getText().toString(),
                                "+" + ccp.getFullNumber(), phoneNumberTextInput.getEditText().getText().toString(),
                                passwordTextInput.getEditText().getText().toString());
                        requestByPhone();
                        String numberPhone = user.getExt() + user.getPhone();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                numberPhone,        // Phone number to verify
                                120,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                (Activity) getContext(),               // Activity (for callback binding)
                                mCallbacks);
                        Toast.makeText(getContext(), "Espere un momento", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textWatchers();
    }

    private void signIn() {
        //String pinValue = pinview.getValue();
        //Toast.makeText(getContext(), pinValue, Toast.LENGTH_SHORT).show();
        if (pinview.getValue().equals("")) return;
        signInWithCredentials(PhoneAuthProvider.getCredential(verificationId, pinview.getValue()));
    }

    private void signInWithCredentials(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser u = task.getResult().getUser();
                            long creationTimestamp = u.getMetadata().getCreationTimestamp();
                            long lastSignInTimestamp = u.getMetadata().getLastSignInTimestamp();
                            if (creationTimestamp == lastSignInTimestamp) {
                                Toast.makeText(getContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                                presenter.enrollUser(user, FirebaseInstanceId.getInstance().getToken());
                            } else {
                                Toast.makeText(getContext(), "Ya existe un usuario con ese número", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        } else {
                            //todo checar que hacer cuando no se pudo registrar
                            dismiss();
                            //Toast.makeText(getContext(), "Sign in failed, display a message and update the UI", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "Hubo un problema", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private void requestByPhone() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithCredentials(phoneAuthCredential);
                String a = phoneAuthCredential.getSmsCode();
                Toast.makeText(getContext(), a, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getContext(), "Código enviado, espere un momento", Toast.LENGTH_SHORT).show();
                verificationId = s;
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getContext(), "Invalid request", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(getContext(), "The SMS quota for the project has been exceeded", Toast.LENGTH_SHORT).show();
                }
                phoneNumberTextInput.getEditText().setEnabled(true);
                phone = false;
                verifyNumberTextView.setText("Hubo un error. Corrobore su número");
                phoneNumberTextInput.getEditText().setText("");
            }
        };
    }

    private void modifyViews() {
        registerLinearLayout.removeView(multiActionButton);
        registerLinearLayout.addView(pinview);
        registerLinearLayout.addView(multiActionButton);
        multiActionButton.setText("VERIFICAR CÓDIGO");
    }

    public static boolean name;
    public static boolean lastName;
    public static boolean phone;
    public static boolean pass;
    public static boolean cPass;
    private EnrollTextWatcher textWatcher;
    public static String sPass;
    public static String sCPass;

    private void textWatchers() {
        //textWatcher = new EnrollTextWatcher(this, nameTextInput, 0);
        //nameTextInput.getEditText().addTextChangedListener(textWatcher);
//
        //textWatcher = new EnrollTextWatcher(this, lastNameTextInput, 1);
        //lastNameTextInput.getEditText().addTextChangedListener(textWatcher);
//
        //textWatcher = new EnrollTextWatcher(this, phoneNumberTextInput, 2);
        //phoneNumberTextInput.getEditText().addTextChangedListener(textWatcher);
//
        //textWatcher = new EnrollTextWatcher(this, passwordTextInput, 3);
        //passwordTextInput.getEditText().addTextChangedListener(textWatcher);
//
        //textWatcher = new EnrollTextWatcher(this, confirmPasswordTextInput, 4);
        //confirmPasswordTextInput.getEditText().addTextChangedListener(textWatcher);

        nameTextInput.getEditText().addTextChangedListener(new EnrollTextWatcher(this, nameTextInput, 0));

        lastNameTextInput.getEditText().addTextChangedListener(new EnrollTextWatcher(this, lastNameTextInput, 1));

        phoneNumberTextInput.getEditText().addTextChangedListener(new EnrollTextWatcher(this, phoneNumberTextInput, 2));

        passwordTextInput.getEditText().addTextChangedListener(new EnrollTextWatcher(this, passwordTextInput, 3));

        confirmPasswordTextInput.getEditText().addTextChangedListener(new EnrollTextWatcher(this, confirmPasswordTextInput, 4));
    }

    private void setViewsEnable(boolean b) {
        nameTextInput.getEditText().setEnabled(b);
        lastNameTextInput.getEditText().setEnabled(b);
        phoneNumberTextInput.getEditText().setEnabled(b);
        passwordTextInput.getEditText().setEnabled(b);
        confirmPasswordTextInput.getEditText().setEnabled(b);
    }

    private void inits(View v) {
        mAuth = FirebaseAuth.getInstance();
        nameTextInput = v.findViewById(R.id.nameTextInput);
        lastNameTextInput = v.findViewById(R.id.lastNameTextInput);
        phoneNumberTextInput = v.findViewById(R.id.phoneNumberTextInput);
        passwordTextInput = v.findViewById(R.id.passwordTextInput);
        confirmPasswordTextInput = v.findViewById(R.id.confirmPasswordTextInput);
        ccp = v.findViewById(R.id.ccp);
        pinview = v.findViewById(R.id.pinView);
        multiActionButton = v.findViewById(R.id.multiActionButton);
        registerLinearLayout = v.findViewById(R.id.registerLinearLayout);
        registerLinearLayout.removeView(pinview);
        verifyNumberTextView = v.findViewById(R.id.verifyNumberTextView);

        presenter = new EnrollPresenter(this);
    }

    @Override
    public void onAuthSuccess(String message) {
        dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInsertDataError(User user) {

    }

    @Override
    public void showProgressBar(String message) {

    }

    @Override
    public void hideProgressBar() {

    }
}
