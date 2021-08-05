package com.idax.ventax.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idax.ventax.Activity.EntrepreneurExtra.EntrepreneurExtraActivity;
import com.idax.ventax.Activity.Menu.MenuActivity;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.FAQ;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.Schedule;
import com.idax.ventax.Entity.SocialMedia;
import com.idax.ventax.Extra.Util;
import com.idax.ventax.Fragment.FAQ.FAQFragment;
import com.idax.ventax.R;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_FAQ_LIST;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INT_FOR_FAQ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;
import static com.idax.ventax.Extra.Constansts.INTENT_COMPANY;
import static com.idax.ventax.Extra.Constansts.INTENT_FAQ_LIST;
import static com.idax.ventax.Extra.Constansts.NOT_AFFILIATE;
import static com.idax.ventax.Extra.Constansts.POSITION_BOTTOM;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_COMPANY_BOTTOMSHEET;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_FAQs;
import static com.idax.ventax.Extra.Constansts.mainURL;

public class AboutCompanyDialog extends DialogFragment {

    private Company company;
    private Design design;
    private List<Schedule> scheduleList;
    private List<SocialMedia> socialMediaList;
    private List<FAQ> faqList;

    public AboutCompanyDialog(EntrepreneurModel modelEntrepreneur) {
        company = modelEntrepreneur.getCompany();
        design = modelEntrepreneur.getDesign();
        scheduleList = modelEntrepreneur.getScheduleList();
        socialMediaList = modelEntrepreneur.getSocialMediaList();
        faqList = modelEntrepreneur.getFaqList();
    }

    private CardView cardView;
    private ImageView logoImageView;
    private TextView showScheduleTextView;
    private TextView adBusinessTextView;
    private TextView businessNameTextView;
    private LinearLayout socialMediaLinearLayout;
    private Button faqButton;

    private TextView picassoTextView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_about_company, null, false);
        inits(v);
        setData();
        listeners();
        dialog.setView(v);
        //listeners();
        AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        return alertDialog;
    }

    private void listeners() {
        showScheduleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ScheduleDialog(
                        getContext(),
                        scheduleList,
                        null,
                        showScheduleTextView,
                        null,
                        design.getMain_background(),
                        POSITION_BOTTOM).createMenuInformation();
            }
        });
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faqList.size() != 0) {
                    dismiss();
                    Intent intent = new Intent(getContext(), EntrepreneurExtraActivity.class);
                    intent.putExtra(INTENT_FAQ_LIST, (Serializable) faqList);
                    intent.putExtra(INTENT_COMPANY, company);
                    intent.putExtra(BUNDLE_INT_FOR_FAQ, NOT_AFFILIATE);
                    startActivityForResult(intent, REQUEST_CODE_FAQs);
                    getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } else
                    Toast.makeText(getContext(), "La empresa aún no cuenta con preguntas frecuentes", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setData() {
        cardView.setCardBackgroundColor(Color.parseColor(design.getDialog_background()));

        businessNameTextView.setText(company.getName());
        businessNameTextView.setTextColor(Color.parseColor(design.getTitle_color()));

        adBusinessTextView.setText(company.getDescription());
        adBusinessTextView.setTextColor(Color.parseColor(design.getText_color()));

        showScheduleTextView.setTextColor(Color.parseColor(design.getText_color()));

        new Util.ConfigSocialMedia(getActivity(), socialMediaLinearLayout).fillSocialMedia(socialMediaList);
    }

    private void inits(View v) {
        cardView = v.findViewById(R.id.cardView);
        logoImageView = v.findViewById(R.id.logoImageView);
        businessNameTextView = v.findViewById(R.id.businessNameTextView);
        showScheduleTextView = v.findViewById(R.id.showScheduleTextView);
        adBusinessTextView = v.findViewById(R.id.adBusinessTextView);
        socialMediaLinearLayout = v.findViewById(R.id.socialMediaLinearLayout);
        faqButton = v.findViewById(R.id.faqButton);
        picassoTextView = v.findViewById(R.id.picassoTextView);

        String url = MessageFormat.format("{0}IMAGES/{1}_{2}/{2}.jpg",
                mainURL, company.getId(), company.getName().toUpperCase());
        Glide.with(getContext())
                .load(url)
                .error(0)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(logoImageView);
    }
}
