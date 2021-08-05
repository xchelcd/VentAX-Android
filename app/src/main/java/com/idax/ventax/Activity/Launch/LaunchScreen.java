package com.idax.ventax.Activity.Launch;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.idax.ventax.Activity.Login.LoginActivity;
import com.idax.ventax.Activity.Menu.MenuActivity;
import com.idax.ventax.DataPersistence.DataPersistence;
import com.idax.ventax.Entity.LoginModel;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.Internet;

import static com.idax.ventax.DataPersistence.DataPersistence.enrollCompanyDataPersistence;
import static com.idax.ventax.DataPersistence.DataPersistence.isFirstTimeDataPersistence;
import static com.idax.ventax.DataPersistence.DataPersistence.userDataPersistence;
import static com.idax.ventax.Extra.Constansts.INTENT_MODEL_LOGIN;
import static com.idax.ventax.Extra.Constansts.SHARD_PREFERENCES_USER;

public class LaunchScreen extends AppCompatActivity implements LaunchView {

    private LaunchPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        presenter = new LaunchPresenter(this);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        //todo descomentar if else
        isFirstTimeDataPersistence = new DataPersistence(getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE));
        /*if (!isFirstTimeDataPersistence.isFirstTime()) seeTutorial();
        else*/ checkDataSaved();

        //test();
    }

    private void seeTutorial() {
        isFirstTimeDataPersistence.setFirstTime(true);
    }

    private void test() {
        userDataPersistence = new DataPersistence(getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE));
        User user = userDataPersistence.getUserData();
        presenter.test(user);
    }

    private void checkDataSaved() {
        userDataPersistence = new DataPersistence(getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE));
        User user = userDataPersistence.getUserData();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        if (Internet.isOnline(getApplicationContext())) presenter.GetCheckUserByUserCredentials(user);
        else LogInWithoutInternet(user);
    }

    private void LogInWithoutInternet(User user) {
        LoginModel loginModel = new LoginModel();
        loginModel.setUser(user);
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(INTENT_MODEL_LOGIN, loginModel);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Sin internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserSaved(LoginModel modelLogin) {
        credentials(modelLogin);
    }

    @Override
    public void onError(String message) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void credentials(LoginModel modelLogin) {
        userDataPersistence.saveUserData(modelLogin.getUser());
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(INTENT_MODEL_LOGIN, modelLogin);
        startActivity(intent);
        finish();
    }

}
