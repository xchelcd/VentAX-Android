package com.idax.ventax.Activity.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LauncherActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.idax.ventax.Activity.Menu.MenuActivity;
import com.idax.ventax.DataPersistence.DataPersistence;
import com.idax.ventax.Dialog.Enroll.EnrollDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.LoginModel;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.io.Serializable;
import java.util.List;

import static com.idax.ventax.DataPersistence.DataPersistence.userDataPersistence;
import static com.idax.ventax.Extra.Constansts.INTENT_COMPANY_LIST;
import static com.idax.ventax.Extra.Constansts.INTENT_MODEL_LOGIN;
import static com.idax.ventax.Extra.Constansts.INTENT_USER;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_REGISTER;
import static com.idax.ventax.Extra.Constansts.SHARD_PREFERENCES_USER;
import static com.idax.ventax.Extra.Util.customNotification;
import static com.idax.ventax.Extra.Util.formatDecimal;
import static com.idax.ventax.Extra.Util.loginView;

@SuppressLint("ParcelCreator")
public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter presenter;
    private Button loginButton;
    private Button registerButton;
    private EditText userNameEditText;
    private EditText passwordEditText;

    private Intent intent;

    private LoadingDialog dialog;

    //private DataPersistence userDataPersistence  = new DataPersistence(getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE));;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        inits();
        //checkDataSaved();
        listeners();
    }

    private void checkDataSaved() {
        userDataPersistence = new DataPersistence(getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE));
        User user = userDataPersistence.getUserData();
        if (user == null) return;
        presenter.GetCheckUserByUserCredentials(user);
    }

    public void prueba() {
        //OrderSQLite o1 = new OrderSQLite(0,0,"IDAX","product1",2,150.0,"cooment");
        //OrderSQLite o2 = new OrderSQLite(1,0,"IDAX","product2",2,150.0,"cooment");
        //OrderSQLite o3 = new OrderSQLite(2,1,"Veterinaria","product3",2,150.0,"cooment");
        //OrderSQLite o4 = new OrderSQLite(3,1,"Veterinaria","product4",2,150.0,"cooment");
        //OrderSQLite o5 = new OrderSQLite(4,2,"Cookies","product5",2,150.0,"cooment");
        //AppDataBase db = AppDataBase.getAppDataBase(getApplicationContext());
        //OrderDao orderDao = db.orderDao();
        //orderDao.insertOrder(o1);
        //orderDao.insertOrder(o2);
        //orderDao.insertOrder(o3);
        //orderDao.insertOrder(o4);
        //orderDao.insertOrder(o5);
        //List<OrderSQLite> oList = orderDao.getAllOrders();
        ////orderDao.deleteOrders(oList);
        //oList = orderDao.getAllOrders();
        //String str = "https://image.tmdb.org/t/p/w92/dRLSoufWtc16F5fliK4ECIVs56p.jpg";
        //str = "https://upload.wikimedia.org/wikipedia/commons/0/01/Low_-_First_Avenue_Star.jpg";
        //str = "https://idaxmx.com/Ticket/business_images/coverPage/1IDAX.jpg";
        ////str = "https://www.publicdomainpictures.net/pictures/320000/velka/background-image.png";
        //Glide.with(this)
        //        .load(str)
        //        .placeholder(R.drawable.progress_bar_image)
        //        .diskCacheStrategy(DiskCacheStrategy.ALL)
        //        //.skipMemoryCache(false)
        //        .signature(new ObjectKey(Math.random()))
        //        //.placeholder(Glide.with())
        //        .listener(new RequestListener<Drawable>() {
        //            @Override
        //            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        //                Toast.makeText(LoginActivity.this, "NO", Toast.LENGTH_SHORT).show();
        //                return false;
        //            }
//
        //            @Override
        //            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        //                Toast.makeText(LoginActivity.this, "CHI", Toast.LENGTH_SHORT).show();
        //                return false;
        //            }
        //        })
        //        .into((ImageView) findViewById(R.id.testGlide));
    }

    private void listeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("11","noti", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNameEditText.getText().toString().trim().isEmpty() || passwordEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ingrese su número y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.GetUserByCredentialsAndCheckAffiliate(userNameEditText.getText().toString(), passwordEditText.getText().toString());

                //presenter.GetUserByCredentials2(userNameEditText.getText().toString(), passwordEditText.getText().toString());// DEPRECATE
                //prueba();
                //presenter.test();
                //customNotification(getApplicationContext(), "Hola mundo", "Como estás", "channel", 2, true);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //startActivityForResult(intent, REQUEST_CODE_REGISTER);
                new EnrollDialog().show(getSupportFragmentManager(), "RegisterDialog");
                userNameEditText.setText("");
                passwordEditText.setText("");
            }
        });
    }

    private void inits() {
        presenter = new LoginPresenter(this, this);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        //adminButton = findViewById(R.id.adminButton);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        dialog = new LoadingDialog(LoginActivity.this);

        loginView = this;
    }

    @Override
    public void onHasDataSaved(LoginModel modelLogin) {
        //TODO CREAR ARCHIVO PHP QUE CORROBORE AL USUARIO GUARDADO Y DEVUELVA UN MODE LOGIN CON EL USUARIO ACTUALIZADO
        credentials(modelLogin);
    }

    @Override
    public void onSuccessCredentials(LoginModel modelLogin) {
        credentials(modelLogin);
    }

    private void credentials(LoginModel modelLogin) {
        //TODO DESCOMENTAR PARA CUANDO TENGA EL ARCHIVO PHP -> userDataPersistence.saveUserData(modelLogin.getUser());
        userDataPersistence.saveUserData(modelLogin.getUser());
        intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra(INTENT_MODEL_LOGIN, modelLogin);
        startActivity(intent);
        finish();
    }

    @Override
    public void onErrorCredentials(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinalErrorRequest(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinalSuccessRequest(User user, List<Company> companyList) {
        onSuccessfulGetAllData(user, companyList);
    }

    @Override
    public void onSuccessUserSaved(LoginModel modelLogin) {
        credentials(modelLogin);
    }

    private void onSuccessfulGetAllData(User user, List<Company> companyList) {//DEPRECATE
        intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra(INTENT_USER, user);
        if (companyList != null) intent.putExtra(INTENT_COMPANY_LIST, (Serializable) companyList);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgressBar(String message) {
        dialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        dialog.closeLoadingDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_REGISTER:
                if (resultCode == RESULT_OK) {

                } else {

                }
        }
    }
}