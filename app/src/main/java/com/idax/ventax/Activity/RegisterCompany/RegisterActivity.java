package com.idax.ventax.Activity.RegisterCompany;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.idax.ventax.Activity.Tutorial.TutorialActivity;
import com.idax.ventax.Adapter.TutorialCompany.TutorialAdapter;
import com.idax.ventax.DataPersistence.DataPersistence;
import com.idax.ventax.Entity.Category;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.io.IOException;
import java.util.List;

import static android.content.Intent.EXTRA_USER;
import static com.idax.ventax.DataPersistence.DataPersistence.enrollCompanyDataPersistence;
import static com.idax.ventax.Extra.Constansts.INTENT_USER;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_COMPANY_PHOTO;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_TUTORIAL;
import static com.idax.ventax.Extra.Constansts.RESULT_CODE_GOT_TO_MENU;
import static com.idax.ventax.Extra.Constansts.RESULT_CODE_GO_TO_TUTORIAL;
import static com.idax.ventax.Extra.Constansts.SHARD_PREFERENCES_USER;
import static com.idax.ventax.Extra.Util.customNotification;
import static com.idax.ventax.Extra.Util.getCategory;
import static com.idax.ventax.Extra.Util.getCategoryList;
import static com.idax.ventax.Extra.Util.isEmailValid;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenter presenter;

    private Button startBusinessButton;
    private TextView categoryTextView;

    //private ViewPager2 tutorialViewPager;
    private TutorialAdapter tutorialAdapter;

    //private RecyclerView recyclerView;
    //private CategoryAdapter categoryAdapter;
    private RadioGroup categoryRadioGroup;
    //private int categoryId = 0;

    private User user;

    private TextInputEditText companyNameTextInput;
    private TextInputEditText descriptionCompanyTextInput;
    private TextInputEditText addressCompanyTextInput;
    private TextInputEditText emailTextInput;
    private TextInputEditText emailConfirmTextInput;
    private ImageButton backImageButton;
    private ImageView mainPohotoImageView;
    private Bitmap bitmap;

    private String name;
    private String description;
    private String address;
    private String email;
    private String emailConfirm;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_register);
        inits();
        setAdapter();
        fillRadioGroup();
        listeners();
        //setAdapterCategory();
    }

    private void listeners() {
        startBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int categoryId = categoryRadioGroup.indexOfChild(categoryRadioGroup.findViewById(categoryRadioGroup.getCheckedRadioButtonId()));
                //Toast.makeText(RegisterActivity.this, a + "->" + getCategory(getApplicationContext(), a), Toast.LENGTH_SHORT).show();
                if (checkData())
                    if (checkEmail())
                        presenter.EnrollCompany(user.getId(), name, description, categoryId, user.getToken(), address, email, bitmap);
                    else emailTextInput.setError("No es un email válido");

                //intent = new Intent(RegisterActivity.this, TutorialActivity.class);
                //intent.putExtra(EXTRA_USER, user);
                //startActivityForResult(intent, REQUEST_CODE_TUTORIAL);
            }
        });
        categoryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                categoryTextView.setText(getCategory(getApplicationContext(), categoryRadioGroup.indexOfChild(categoryRadioGroup.findViewById(categoryRadioGroup.getCheckedRadioButtonId()))));
            }
        });
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        emailTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailTextInput.setError(null);
                emailConfirmTextInput.setError(null);
            }
        });
        mainPohotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Cambiar foto de la empresa", Toast.LENGTH_SHORT).show();
                openGallery(REQUEST_CODE_COMPANY_PHOTO);
            }
        });
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecione una aplicación"), requestCode);
    }

    private boolean checkEmail() {
        email = emailTextInput.getText().toString();
        emailConfirm = emailConfirmTextInput.getText().toString();
        if (email.trim().equals(emailConfirm.trim())) return isEmailValid(email);
        else {
            emailTextInput.setError("No coinciden");
            emailConfirmTextInput.setError("No coinciden");
            return false;
        }

    }

    private boolean checkData() {
        name = companyNameTextInput.getText().toString();
        description = descriptionCompanyTextInput.getText().toString();
        address = addressCompanyTextInput.getText().toString();
        return !name.isEmpty() && !description.isEmpty();
    }

    private void fillRadioGroup() {
        List<Category> categories = getCategoryList(this);
        for (Category c : categories) {
            RadioButton radioButton = new RadioButton(this);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            l.setMargins(15, 0, 15, 0);
            radioButton.setLayoutParams(l);
            radioButton.setTag(c.getId());
            radioButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            radioButton.setButtonDrawable(null);
            radioButton.setBackground(getResources().getDrawable(R.drawable.style_radio_button));
            radioButton.setPadding(10, 0, 10, 0);
            radioButton.setText(getCategory(this, c.getId()));
            radioButton.setTextColor(getColor(R.color.blackColorVentax));
            categoryRadioGroup.addView(radioButton);
        }
        ((RadioButton) categoryRadioGroup.getChildAt(0)).setChecked(true);
        categoryTextView.setText(getCategory(getApplicationContext(), 0));
    }

    private void setAdapter() {
        //tutorialViewPager.setClipToPadding(false);
        //tutorialViewPager.setClipChildren(false);
        //tutorialViewPager.setOffscreenPageLimit(3);
        //tutorialViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        //tutorialViewPager.setAdapter(tutorialAdapter);
        //CompositePageTransformer transformer = new CompositePageTransformer();
        //transformer.addTransformer(new MarginPageTransformer(8));
        //transformer.addTransformer(new ViewPager2.PageTransformer() {
        //    @Override
        //    public void transformPage(@NonNull View page, float position) {
        //        float v = 1 - Math.abs(position);
        //        page.setScaleY(0.8f + v * 0.2f);
        //    }
        //});
        //tutorialViewPager.setPageTransformer(transformer);
    }

    private void inits() {
        presenter = new RegisterPresenter(this);
        user = (User) getIntent().getSerializableExtra(INTENT_USER);
        //tutorialAdapter = new TutorialAdapter(getApplicationContext());
        //tutorialViewPager = findViewById(R.id.tutorialViewPager);
        //recyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRadioGroup = findViewById(R.id.categoryRadioGroup);
        startBusinessButton = findViewById(R.id.startBusinessButton);
        categoryTextView = findViewById(R.id.categoryTextView);

        mainPohotoImageView = findViewById(R.id.mainPohotoImageView);

        companyNameTextInput = findViewById(R.id.companyNameTextInput);
        descriptionCompanyTextInput = findViewById(R.id.descriptionCompanyTextInput);
        addressCompanyTextInput = findViewById(R.id.addressCompanyTextInput);
        emailTextInput = findViewById(R.id.emailTextInput);
        emailConfirmTextInput = findViewById(R.id.emailConfirmTextInput);
        backImageButton = findViewById(R.id.backImageButton);

        loading = new LoadingDialog(RegisterActivity.this);
        enrollCompanyDataPersistence = new DataPersistence(getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE));
    }

    private void receiveData(Intent data) {
        try {
            Uri path = data.getData();
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
            mainPohotoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mainPohotoImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LoadingDialog loading;

    @Override
    public void showProgressBar(String message) {
        loading.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        loading.closeLoadingDialog();
    }

    @Override
    public void OnSuccessEnrollCompany(String message) {
        enrollCompanyDataPersistence.setStateRequestEnroll(true);
        //createNotification(getString(R.string.welcome_to_vax), getString(R.string.welcome_to_vax_body), user.getId(), getApplicationContext());
        customNotification(getApplicationContext(), message, "En breve será notificado sobre la aprobación de su solicitud para que pueda usar todas las funcionalidades de VAX", "channel2", 2, false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        intent = new Intent(RegisterActivity.this, TutorialActivity.class);
        intent.putExtra(EXTRA_USER, user);
        startActivityForResult(intent, REQUEST_CODE_TUTORIAL);
    }

    @Override
    public void OnErrorEnrollCompany(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_TUTORIAL:
                if (resultCode == RESULT_CODE_GO_TO_TUTORIAL) {
                    setResult(RESULT_CODE_GOT_TO_MENU);
                    onBackPressed();
                }
                break;
            case REQUEST_CODE_COMPANY_PHOTO:
                try {
                    receiveData(data);
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (resultCode != RESULT_OK) {
                    Toast.makeText(RegisterActivity.this, R.string.error_update_photo, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}