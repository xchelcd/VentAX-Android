package com.idax.ventax.Fragment.Company;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idax.ventax.Activity.Menu.MenuUpdateObjects;
import com.idax.ventax.Conn.Delete;
import com.idax.ventax.Dialog.DDCompany.DeleteCompanyDialog;
import com.idax.ventax.Dialog.DDCompany.DesactivateCompanyDialog;
import com.idax.ventax.Dialog.InformationDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.io.IOException;
import java.text.MessageFormat;

import static android.app.Activity.RESULT_OK;
import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_COMPANY_PHOTO;
import static com.idax.ventax.Extra.Constansts.mainURL;
import static com.idax.ventax.Extra.Util.getAccountType;
import static com.idax.ventax.Extra.Util.getCategory;

public class CompanyFragment extends Fragment implements CompanyFView {

    private ViewGroup view;

    private LoadingDialog dialog;

    private CompanyFPresenter presenter;
    private CompanyFView companyFView = this;

    private User user;
    private Company company;
    private MenuUpdateObjects updateObjects;

    private ImageView mainPohotoImageView;
    private TextView companyNameTextView;
    private TextView companyDescriptionTextView;
    private TextView companyCategoryTextView;
    private TextView companyAccountTypeTextView;
    private TextView titleTextView;
    private TextView companyAreaDeliveryTextView;
    private TextView stateCompanyTextView;

    private Button changeAccountTypeButton;
    private Button showStadisticsButton;
    private Button deleteCompanyButton;
    private Button waitCompanyButton;

    private Bitmap bitmap;

    private String companyName;

    private DesactivateCompanyDialog desactivateCompanyDialog;
    private DeleteCompanyDialog deleteCompanyDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            receiveData(data);
        } catch (Exception e) {
            Toast.makeText(getContext(), "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == REQUEST_CODE_COMPANY_PHOTO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Subiendo imagen", Toast.LENGTH_SHORT).show();
                presenter.UploadCompanyPhoto(bitmap, company.getId(), company.getName());
            } else {
                Toast.makeText(getContext(), R.string.error_update_photo, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            company = (Company) getArguments().get(BUNDLE_COMPANY);
            user = (User) getArguments().get(BUNDLE_USER);
            updateObjects = (MenuUpdateObjects) getArguments().get(BUNDLE_INTERFACE_OBJ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_company, container, false);
        inits(view);
        setData();
        listeners();
        return view;
    }

    private void setData() {
        /* /IMAGE/id_COMPANY/COMPANY.jpg */
        String url = MessageFormat.format("{0}IMAGES/{1}_{2}/{3}.jpg",
                mainURL, company.getId(), companyName, companyName);
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.progress_bar_image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .error(getResources().getDrawable(R.drawable.ic_baseline_insert_photo_24))
                .into(mainPohotoImageView);
        titleTextView.setText(company.getName());
        //titleTextView.setText(MessageFormat.format("{0} ({1})",
        //        company.getName(), Util.getAccountType(getContext(),
        //                company.getPriority())));
        companyNameTextView.setText((user.getAffiliate() == AFFILIATE) ? company.getName() : getString(R.string.sin_registro));
        companyDescriptionTextView.setText((user.getAffiliate() == AFFILIATE) ? company.getDescription() : getString(R.string.sin_registro));
        companyCategoryTextView.setText((user.getAffiliate() == AFFILIATE) ? getCategory(getContext(), company.getCategory()) : getString(R.string.sin_registro));
        companyAccountTypeTextView.setText((user.getAffiliate() == AFFILIATE) ? getAccountType(getContext(), company.getPriority()) : getString(R.string.sin_registro));
        companyAreaDeliveryTextView.setText(
                (user.getAffiliate() == AFFILIATE) ? (company.getAddress() == null) ?
                        getString(R.string.no_especificado) : company.getAddress() : getString(R.string.sin_registro));
        setStateCompnay(company.getActive());
    }

    private void setStateCompnay(int state) {
        stateCompanyTextView.setText((state == 1) ? "Activo" : "Desactivado");
        waitCompanyButton.setText((state == 1) ? "SUSPENDER MI EMPRESA" : "ACTIVAR MI EMPRESA");
    }

    private void listeners() {
        changeAccountTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SIN FUNCIONALIDAD.\nSi deseas hacer uso de esta opción comunícate con idax a través de nuestro FB: /idax.mx", Toast.LENGTH_LONG).show();
            }
        });
        showStadisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SIN FUNCIONALIDAD.\nSi deseas hacer uso de esta opción comunícate con idax a través de nuestro FB: /idax.mx", Toast.LENGTH_LONG).show();
            }
        });
        companyAccountTypeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InformationDialog(getContext(),
                        getString(R.string.information_account_type),
                        null, companyAccountTypeTextView, null).createMenuInformation();
            }
        });

        deleteCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "SIN FUNCIONALIDAD.\nSi deseas hacer uso de esta opción comunícate con idax a través de nuestro FB: /idax.mx", Toast.LENGTH_LONG).show();
                deleteCompanyDialog = new DeleteCompanyDialog(companyFView, presenter, company.getId(), -1, true);
                deleteCompanyDialog.show(getParentFragmentManager(), "DeleteCompanyDialog");
            }
        });

        mainPohotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Cambiar foto de la empresa", Toast.LENGTH_SHORT).show();
                openGallery(REQUEST_CODE_COMPANY_PHOTO);
            }
        });

        titleTextView.setOnClickListener(v -> {
            getSlidingRootNav(null).openMenu(true);
        });

        waitCompanyButton.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "SIN FUNCIONALIDAD.\nSi deseas hacer uso de esta opción comunícate con idax a través de nuestro FB: /idax.mx", Toast.LENGTH_LONG).show();
            desactivateCompanyDialog = new DesactivateCompanyDialog(companyFView, presenter, company.getId(), company.getActive(), false);
            desactivateCompanyDialog.show(getParentFragmentManager(), "DesactivateCompanyDialog");
        });
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecione una aplicación"), requestCode);
    }

    private void inits(ViewGroup v) {
        presenter = new CompanyFPresenter(companyFView);
        dialog = new LoadingDialog(getContext());

        mainPohotoImageView = v.findViewById(R.id.mainPohotoImageView);
        companyNameTextView = v.findViewById(R.id.companyNameTextView);
        companyDescriptionTextView = v.findViewById(R.id.companyDescriptionTextView);
        companyCategoryTextView = v.findViewById(R.id.companyCategoryTextView);
        companyAccountTypeTextView = v.findViewById(R.id.companyAccountTypeTextView);

        companyAreaDeliveryTextView = v.findViewById(R.id.companyAreaDeliveryTextView);
        stateCompanyTextView = v.findViewById(R.id.stateCompanyTextView);

        titleTextView = v.findViewById(R.id.titleTextView);
        changeAccountTypeButton = v.findViewById(R.id.changeAccountTypeButton);
        showStadisticsButton = v.findViewById(R.id.showStadisticsButton);
        deleteCompanyButton = v.findViewById(R.id.deleteCompanyButton);
        waitCompanyButton = v.findViewById(R.id.waitCompanyButton);
        companyName = company.getName().toUpperCase();
    }

    private void receiveData(Intent data) {
        try {
            Uri path = data.getData();
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
            mainPohotoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mainPohotoImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnUpdatePhotoSuccess() {
        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDesactivateSuccess(int state, String message) {
        company.setActive(state);
        setStateCompnay(state);
        updateObjects.UpdateCompany(company);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        desactivateCompanyDialog.dismiss();
    }

    @Override
    public void onDesactiveError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCompanySuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCompanyError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(String message) {
        dialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        dialog.closeLoadingDialog();
    }
}
