package com.idax.ventax.Dialog.DDCompany;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idax.ventax.Fragment.Company.CompanyFPresenter;
import com.idax.ventax.Fragment.Company.CompanyFView;
import com.idax.ventax.R;

public class DesactivateCompanyDialog extends DialogFragment {

    private boolean type;
    private CompanyFView companyFView;
    private CompanyFPresenter presenter;
    private int companyId;
    private int state;

    public DesactivateCompanyDialog(CompanyFView companyFView, CompanyFPresenter presenter, int companyId, int state, boolean type) {
        //type true -> delete
        //type false -> desactivate
        this.type = type;
        this.companyFView = companyFView;
        this.presenter = presenter;
        this.companyId = companyId;
        this.state = state;
    }

    private Button cancelButton;
    private Button noCancelButton;
    private TextView typeTitleTextView;
    private TextView titleTextView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dd_company, null, false);
        builder.setView(v);
        inits(v);
        listeners();
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private void listeners() {
        cancelButton.setOnClickListener(v -> {
            dismiss();
        });

        noCancelButton.setOnClickListener(v -> {
            if (type) {
                presenter.DeleteStateCompany(companyId);
            } else {
                if (state == 1) {
                    presenter.ChangeStateCompany(companyId, 0);
                } else {
                    presenter.ChangeStateCompany(companyId, 1);
                }
            }

        });
    }

    private void inits(View v) {
        cancelButton = v.findViewById(R.id.cancelButton);
        noCancelButton = v.findViewById(R.id.noCancelButton);
        typeTitleTextView = v.findViewById(R.id.typeTitleTextView);
        titleTextView = v.findViewById(R.id.titleTextView);
        setData();
    }

    private void setData() {
        if (type) {
            typeTitleTextView.setText("Eliminar");
            titleTextView.setText("¿Está seguro que desea eliminar su empresa de forma permanente?");
        } else {
            String type = state == 1 ? "Tomar un descanso" : "Volver al juego";
            String title = state == 1 ? "¿Estas seguro que deseas desactivar tu cuenta?" : "¿Activar empresa?";
            typeTitleTextView.setText(type);

            titleTextView.setText(title);
        }
    }
}
