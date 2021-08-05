package com.idax.ventax.BottomSheet;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.idax.ventax.Activity.Entrepreneur.EntrepreneurActivity;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.Schedule;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Dialog.ScheduleDialog;
import com.idax.ventax.Extra.Util;
import com.idax.ventax.R;

import java.text.MessageFormat;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.INTENT_MODEL_ENTREPRENEUR;
import static com.idax.ventax.Extra.Constansts.INTENT_USER;
import static com.idax.ventax.Extra.Constansts.POSITION_TOP;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_COMPANY_BOTTOMSHEET;
import static com.idax.ventax.Extra.Constansts.mainURL;

public class CompanyBottomSheet extends BottomSheetDialogFragment {

    private Intent intent;

    private EntrepreneurModel modelEntrepreneur;
    private Company company;
    private Design design;
    private List<Schedule> scheduleList;
    private User user;

    private ImageView logoImageView;
    private TextView categoryCompnayTextView;
    private TextView descriptionCompanyTextView;
    private TextView nameCompanyTextView;
    private LinearLayout socialMediaLinearLayout;
    private Button goToCompanyButton;
    private TextView picassoTextView;
    private ImageButton scheduleImageButton;

    private Drawable background;


    public CompanyBottomSheet(EntrepreneurModel modelEntrepreneur, User user) {
        this.modelEntrepreneur = modelEntrepreneur;
        company = modelEntrepreneur.getCompany();
        design = modelEntrepreneur.getDesign();
        scheduleList = modelEntrepreneur.getScheduleList();
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_company, null, false);//inflater.inflate(R.layout.bottom_sheet_register, container,false);
        inits(v);
        setData();
        listener();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        bottomSheetDialog.show();
        return bottomSheetDialog;
    }

    private void listener() {
        goToCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), EntrepreneurActivity.class);
                intent.putExtra(INTENT_USER, user);
                intent.putExtra(INTENT_MODEL_ENTREPRENEUR, modelEntrepreneur);
                startActivityForResult(intent, REQUEST_CODE_COMPANY_BOTTOMSHEET);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                dismiss();
            }
        });
        scheduleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ScheduleDialog(
                        getContext(),
                        scheduleList,
                        null,
                        null,
                        scheduleImageButton,
                        design.getDialog_background(),
                        POSITION_TOP).createMenuInformation();
            }
        });
    }

    private void setData() {

        nameCompanyTextView.setText(company.getName());
        nameCompanyTextView.setTextColor(Color.parseColor(design.getTitle_color()));
        descriptionCompanyTextView.setText(company.getDescription());
        descriptionCompanyTextView.setTextColor(Color.parseColor(design.getText_color()));
        categoryCompnayTextView.setText(Util.getCategory(getContext(), company.getCategory()));
        categoryCompnayTextView.setTextColor(Color.parseColor(design.getText_color()));
        ((GradientDrawable) background).setColor(Color.parseColor(design.getCard_background()));
        DrawableCompat.setTint(scheduleImageButton.getBackground(), Color.parseColor(design.getText_color()));
        //logoImageView
        //fillSocialMedia();
        new Util.ConfigSocialMedia(getActivity(), socialMediaLinearLayout).fillSocialMedia(modelEntrepreneur.getSocialMediaList());

    }

    /*@SuppressLint("UseCompatLoadingForDrawables")
    private void fillSocialMedia() {
        for (final SocialMedia sm : modelEntrepreneur.getSocialMediaList()) {
            ImageButton imageButton = new ImageButton(getContext());
            imageButton.setLayoutParams(setLayoutParams());
            imageButton.setBackground(getResources().getDrawable(getDrawable(sm.getSocialmedia_id())));//R.drawable.com_facebook_favicon_blue)
            socialMediaLinearLayout.addView(imageButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToPage(sm.getSocialmedia_id(), sm.getCode());
                }
            });
        }
    }*/


    private void inits(View v) {
        logoImageView = v.findViewById(R.id.logoImageView);
        nameCompanyTextView = v.findViewById(R.id.nameCompanyTextView);
        categoryCompnayTextView = v.findViewById(R.id.categoryCompnayTextView);
        descriptionCompanyTextView = v.findViewById(R.id.descriptionCompanyTextView);
        socialMediaLinearLayout = v.findViewById(R.id.socialMediaLinearLayout);
        goToCompanyButton = v.findViewById(R.id.goToCompanyButton);
        picassoTextView = v.findViewById(R.id.picassoTextView);
        scheduleImageButton = v.findViewById(R.id.scheduleImageButton);
        //fillDayList();

        background = v.findViewById(R.id.bottom_sheet).getBackground();

        String url = MessageFormat.format("{0}IMAGES/{1}_{2}/{2}.jpg",
                mainURL, company.getId(), company.getName());
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.progress_bar_image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(logoImageView);
    }

    /*private int getDrawable(int socialMediaId) {
        if (socialMediaId == 0) {//facebook
            return R.drawable.socialmedia_facebook;
        } else if (socialMediaId == 1) {//instagram
            return R.drawable.socialmedia_instagram;
        } else if (socialMediaId == 2) {//twitter
            return R.drawable.socialmedia_twitter;
        } else if (socialMediaId == 3) {//web
            return R.drawable.socialmedia_web;
        } else if (socialMediaId == 4) {//whatsapp
            return R.drawable.socialmedia_whatsapp;
        }
        return 0;
    }*/

    /*private void goToPage(int socialMediaId, String urlId) {
        GoToSocialMedia sm = new GoToSocialMedia();
        String socialMediaName = "";
        switch (socialMediaId) {
            case 0:
                socialMediaName = "Facebook";
                sm.goToFacebook(urlId);
                break;
            case 1:
                socialMediaName = "Instagram";
                sm.goToInstagram(urlId);
                break;
            case 2:
                socialMediaName = "Twitter";
                sm.goToTwitter(urlId);
                break;
            case 3:
                socialMediaName = "Web";
                sm.goToWeb(urlId);
                break;
            case 4:
                socialMediaName = "WhatsApp";
                sm.goToWhatsApp(urlId);
                break;
        }
    }*/

    /*private LinearLayout.LayoutParams setLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
        layoutParams.setMargins(30, 5, 30, 5);
        return layoutParams;
    }*/

    /*protected class GoToSocialMedia {
        private void goToWhatsApp(String urlId) {//goToWhatsApp
            String msj = "";
            intent = new Intent(Intent.ACTION_VIEW);
            String uri = "whatsapp://send?phone=" + urlId + "&text=" + msj;
            intent.setData(Uri.parse(uri));
            startActivityForResult(intent, 745);
        }

        private void goToWeb(String urlId) {
            startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(urlId)), 986);
        }

        private void goToTwitter(String urlId) {
            Uri uri = Uri.parse("http://twitter.com/" + urlId);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.twitter.android");
            try {
                startActivityForResult(likeIng, 745);
            } catch (ActivityNotFoundException e) {
                startActivityForResult(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://twitter.com/" + urlId)), 745);
            }
        }

        private void goToInstagram(String urlId) {
            Uri uri = Uri.parse("http://instagram.com/_u/" + urlId);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");
            try {
                startActivityForResult(likeIng, 745);
            } catch (ActivityNotFoundException e) {
                startActivityForResult(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/" + urlId)), 745);
            }
        }

        private void goToFacebook(String urlId) {
            String facebookUrl = "https://www.facebook.com/" + urlId;
            try {
                int versionCode = getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                    startActivityForResult(new Intent(Intent.ACTION_VIEW, uri), 745);
                } else {
                    Uri uri = Uri.parse("fb://page/" + urlId);
                    startActivityForResult(new Intent(Intent.ACTION_VIEW, uri), 745);
                }
            } catch (PackageManager.NameNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
            }
        }
    }*/
}
