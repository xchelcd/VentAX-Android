package com.idax.ventax.Fragment.Settings;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.room.util.StringUtil;

import com.google.protobuf.Empty;
import com.idax.ventax.Activity.Menu.MenuUpdateObjects;
import com.idax.ventax.Dialog.InformationDialog;
import com.idax.ventax.Entity.CompanyConfigurationModel;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.Schedule;
import com.idax.ventax.Entity.SocialMedia;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;
import com.idax.ventax.TextWatcher.DesignCustomTextWatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_MODEL_ENTREPRENEURS;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;

public class SettingsFragment extends Fragment implements SettingsFView {

    private ViewGroup view;

    private SettingsFPresenter presenter;

    private User user;
    private EntrepreneurModel modelEntrepreneur;
    private MenuUpdateObjects updateObjects;

    private SettingsFragment.DesignClass designClass;
    private SettingsFragment.SocialMediaClass socialMediaClass;
    private SettingsFragment.ScheduleClass scheduleClass;

    private static final int DESIGN = 0;
    private static final int SOCIALMEDIA = 1;
    private static final int SCHEDULE = 2;
    private static final int TOGGLE_ON = 1;
    private static final int TOGGLE_OFF = 2;

    //private TextView titleTextView;

    private Button saveAllButton;
    private int buttonPressed = -1;
    private LinearLayout showSettingsLinearLayout;
    private Button designButton;
    private Button socialMediaButton;
    private Button scheduleButton;
    private View designView;
    private View designPreView;
    private View socialmediaView;
    private View scheduleView;
    boolean b = false;


    //EditText para cambiar el diseño
    private LinearLayout backgroundMainViewLinearLayout;
    private EditText backgroundMainViewEditText;
    private EditText backgroundMainViewProductEditText;
    private EditText backgroundDetailsProductEditText;
    private EditText colorTitleEditText;
    private EditText colorLetterEditText;
    private ToggleButton numColumnsToggleButton;

    //CARD VIEW
    private TextView titleCardViewTextView;
    private TextView priceCardViewTextView;
    private TextView descriptionCardViewTextView;
    private LinearLayout mainCardViewLinearLayout;
    private CardView mainCardViewCardView;

    //DIALOG
    private TextView prodcutTitleDialogTextView;
    private TextView priceDialogTextView;
    private TextView idDialogTextView;
    private TextView detailsDialogTextView;
    private LinearLayout mainDialogLinearLayout;

    //SOCIAL MEDIA
    private TextView socialMediaInformationTextView;
    private CheckBox facebookEditCheckBox;
    private CheckBox instagramEditCheckBox;
    private CheckBox twitterEditCheckBox;
    private CheckBox webEditCheckBox;
    private EditText facebookEditEditText;
    private EditText instagramEditEditText;
    private EditText twitterEditEditText;
    private EditText webEditEditText;

    //SCHEDULE
    private TextView startMondayTextView;
    private TextView endMondayTextView;
    private TextView startTuesdayTextView;
    private TextView endTuesdayTextView;
    private TextView startWednesdayTextView;
    private TextView endWednesdayTextView;
    private TextView startThursdayTextView;
    private TextView endThursdayTextView;
    private TextView startFridayTextView;
    private TextView endFridayTextView;
    private TextView startSaturdayTextView;
    private TextView endSaturdayTextView;
    private TextView startSundayTextView;
    private TextView endSundayTextView;

    private TextView informationScheduleTextView;

    private TextView designInformationTextView;

    private LoadingDialog loadingDialog;

    private TextView nameFragmentTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modelEntrepreneur = (EntrepreneurModel) getArguments().get(BUNDLE_MODEL_ENTREPRENEURS);
            user = (User) getArguments().get(BUNDLE_USER);
            updateObjects = (MenuUpdateObjects) getArguments().get(BUNDLE_INTERFACE_OBJ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container, false);
        inits(view);
        listeners();
        return view;
    }

    private void listeners() {
        saveAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Design design = new Design((numColumnsToggleButton.isChecked()) ? TOGGLE_OFF : TOGGLE_ON,
                        new StringBuffer().append("#").append(backgroundMainViewEditText.getText().toString()).toString(),
                        new StringBuffer().append("#").append(backgroundMainViewProductEditText.getText().toString()).toString(),
                        new StringBuffer().append("#").append(colorTitleEditText.getText().toString()).toString(),
                        new StringBuffer().append("#").append(colorLetterEditText.getText().toString()).toString(),
                        new StringBuffer().append("#").append(backgroundDetailsProductEditText.getText().toString()).toString());
                List<Schedule> scheduleList = scheduleClass.saveSchedule();
                List<SocialMedia> socialMediaList = socialMediaClass.saveSocialMedia();
                CompanyConfigurationModel ccm = new CompanyConfigurationModel(design, scheduleList, socialMediaList, modelEntrepreneur.getCompany().getId());
                presenter.updateCompanyByCompanyId(ccm);
            }
        });

        designButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = buttonPressed == DESIGN;
                buttonPressed = DESIGN;
                designClass.showView(buttonPressed);
            }
        });

        socialMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = buttonPressed == SOCIALMEDIA;
                buttonPressed = SOCIALMEDIA;
                designClass.showView(buttonPressed);
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = buttonPressed == SCHEDULE;
                buttonPressed = SCHEDULE;
                designClass.showView(buttonPressed);
            }
        });
        nameFragmentTextView.setOnClickListener(v -> {
            getSlidingRootNav(null).openMenu(true);
        });
    }

    private void inits(ViewGroup v) {
        presenter = new SettingsFPresenter(this);
        loadingDialog = new LoadingDialog(getContext());

        nameFragmentTextView = v.findViewById(R.id.nameFragmentTextView);

        //MAIN
        saveAllButton = v.findViewById(R.id.saveAllButton);
        showSettingsLinearLayout = v.findViewById(R.id.showSettingsLinearLayout);
        designButton = v.findViewById(R.id.designButton);
        socialMediaButton = v.findViewById(R.id.socialMediaButton);
        scheduleButton = v.findViewById(R.id.scheduleButton);
        designView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings_set_design, v, false);
        designPreView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings_show_design, v, false);
        socialmediaView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings_socialmedia, v, false);
        scheduleView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings_schedule, v, false);

        //DESIGN
        backgroundMainViewEditText = designView.findViewById(R.id.backgroundMainViewEditText);
        backgroundDetailsProductEditText = designView.findViewById(R.id.backgroundDetailsProductEditText);
        colorTitleEditText = designView.findViewById(R.id.colorTitleEditText);
        colorLetterEditText = designView.findViewById(R.id.colorLetterEditText);
        backgroundMainViewProductEditText = designView.findViewById(R.id.backgroundMainViewProductEditText);
        numColumnsToggleButton = designView.findViewById(R.id.numColumnsToggleButton);

        //PREVIEW DESIGN
        backgroundMainViewLinearLayout = designPreView.findViewById(R.id.backgroundMainViewLinearLayout);

        titleCardViewTextView = designPreView.findViewById(R.id.titleCardViewTextView);
        priceCardViewTextView = designPreView.findViewById(R.id.priceCardViewTextView);
        descriptionCardViewTextView = designPreView.findViewById(R.id.descriptionCardViewTextView);
        mainCardViewLinearLayout = designPreView.findViewById(R.id.mainCardViewLinearLayout);
        mainCardViewCardView = designPreView.findViewById(R.id.mainCardViewCardView);

        prodcutTitleDialogTextView = designPreView.findViewById(R.id.prodcutTitleDialogTextView);
        priceDialogTextView = designPreView.findViewById(R.id.priceDialogTextView);
        idDialogTextView = designPreView.findViewById(R.id.idDialogTextView);
        detailsDialogTextView = designPreView.findViewById(R.id.detailsDialogTextView);
        mainDialogLinearLayout = designPreView.findViewById(R.id.mainDialogLinearLayout);

        designInformationTextView = designView.findViewById(R.id.designInformationTextView);

        //SOCIALMEDIA
        facebookEditCheckBox = socialmediaView.findViewById(R.id.facebookEditCheckBox);
        instagramEditCheckBox = socialmediaView.findViewById(R.id.instagramEditCheckBox);
        twitterEditCheckBox = socialmediaView.findViewById(R.id.twitterEditCheckBox);
        webEditCheckBox = socialmediaView.findViewById(R.id.webEditCheckBox);
        facebookEditEditText = socialmediaView.findViewById(R.id.facebookEditEditText);
        instagramEditEditText = socialmediaView.findViewById(R.id.instagramEditEditText);
        twitterEditEditText = socialmediaView.findViewById(R.id.twitterEditEditText);
        webEditEditText = socialmediaView.findViewById(R.id.webEditEditText);

        socialMediaInformationTextView = socialmediaView.findViewById(R.id.socialMediaInformationTextView);

        //SCHEDULE
        startMondayTextView = scheduleView.findViewById(R.id.startMondayTextView);
        endMondayTextView = scheduleView.findViewById(R.id.endMondayTextView);
        startTuesdayTextView = scheduleView.findViewById(R.id.startTuesdayTextView);
        endTuesdayTextView = scheduleView.findViewById(R.id.endTuesdayTextView);
        startWednesdayTextView = scheduleView.findViewById(R.id.startWednesdayTextView);
        endWednesdayTextView = scheduleView.findViewById(R.id.endWednesdayTextView);
        startThursdayTextView = scheduleView.findViewById(R.id.startThursdayTextView);
        endThursdayTextView = scheduleView.findViewById(R.id.endThursdayTextView);
        startFridayTextView = scheduleView.findViewById(R.id.startFridayTextView);
        endFridayTextView = scheduleView.findViewById(R.id.endFridayTextView);
        startSaturdayTextView = scheduleView.findViewById(R.id.startSaturdayTextView);
        endSaturdayTextView = scheduleView.findViewById(R.id.endSaturdayTextView);
        startSundayTextView = scheduleView.findViewById(R.id.startSundayTextView);
        endSundayTextView = scheduleView.findViewById(R.id.endSundayTextView);

        informationScheduleTextView = scheduleView.findViewById(R.id.informationScheduleTextView);

        //CLASS
        designClass = new SettingsFragment.DesignClass(modelEntrepreneur.getDesign());
        socialMediaClass = new SettingsFragment.SocialMediaClass(modelEntrepreneur.getSocialMediaList());
        scheduleClass = new SettingsFragment.ScheduleClass(modelEntrepreneur.getScheduleList(), scheduleView);
    }

    @Override
    public void showProgressBar(String message) {
        loadingDialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        loadingDialog.closeLoadingDialog();
    }

    @Override
    public void OnSuccessDataSaved(CompanyConfigurationModel ccm) {
        updateObjects.UpdateCompanySettings(ccm.getDesign(), ccm.getScheduleList(), ccm.getSocialMediaList());
    }

    @Override
    public void OnErrorDataSaved() {

    }

    private class DesignClass {

        private Design design;

        public DesignClass(Design design) {
            this.design = design;
            listeners();
            fillDesign();
        }

        private void fillDesign() {
            backgroundMainViewEditText.setText(design.getMain_background().replace("#", ""));
            backgroundMainViewProductEditText.setText(design.getCard_background().replace("#", ""));
            backgroundDetailsProductEditText.setText(design.getDialog_background().replace("#", ""));
            colorTitleEditText.setText(design.getTitle_color().replace("#", ""));
            colorLetterEditText.setText(design.getText_color().replace("#", ""));

            titleCardViewTextView.setTextColor(Color.parseColor(design.getTitle_color()));
            priceCardViewTextView.setTextColor(Color.parseColor(design.getTitle_color()));
            descriptionCardViewTextView.setTextColor(Color.parseColor(design.getText_color()));
            prodcutTitleDialogTextView.setTextColor(Color.parseColor(design.getTitle_color()));
            priceDialogTextView.setTextColor(Color.parseColor(design.getTitle_color()));
            idDialogTextView.setTextColor(Color.parseColor(design.getText_color()));
            detailsDialogTextView.setTextColor(Color.parseColor(design.getText_color()));
            mainCardViewCardView.setCardBackgroundColor(Color.parseColor(design.getCard_background()));
            mainDialogLinearLayout.setBackgroundColor(Color.parseColor(design.getDialog_background()));
            backgroundMainViewLinearLayout.setBackgroundColor(Color.parseColor(design.getMain_background()));

            numColumnsToggleButton.setChecked((design.getColumns() != 1));
        }

        private void showView(int buttonPressed) {
            showSettingsLinearLayout.removeAllViews();

            showSettingsLinearLayout.addView((buttonPressed == DESIGN) ? designView :
                    (buttonPressed == SCHEDULE) ? scheduleView : socialmediaView);
            if (buttonPressed == DESIGN) showSettingsLinearLayout.addView(designPreView);

        }

        private void listeners() {
            designInformationTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new InformationDialog(getContext(), getString(R.string.information_design), null, designInformationTextView, null).createMenuInformation();
                }
            });
            backgroundMainViewEditText.addTextChangedListener(new DesignCustomTextWatcher(backgroundMainViewLinearLayout));
            backgroundMainViewProductEditText.addTextChangedListener(new DesignCustomTextWatcher(mainCardViewLinearLayout));
            backgroundDetailsProductEditText.addTextChangedListener(new DesignCustomTextWatcher(mainDialogLinearLayout));
            colorTitleEditText.addTextChangedListener(new DesignCustomTextWatcher(titleCardViewTextView, prodcutTitleDialogTextView));
            colorLetterEditText.addTextChangedListener(new DesignCustomTextWatcher(descriptionCardViewTextView, priceDialogTextView, idDialogTextView, detailsDialogTextView));
        }
    }

    private class SocialMediaClass {
        private List<SocialMedia> socialMediaList;

        public SocialMediaClass(List<SocialMedia> socialMediaList) {
            this.socialMediaList = socialMediaList;
            fillViewsList();
            listeners();
            fillSocialMedia();
        }

        private void listeners() {
            socialMediaInformationTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new InformationDialog(getContext(),
                            getString(R.string.information_socialmedia),
                            null, socialMediaInformationTextView, null).createMenuInformation();
                }
            });
            facebookEditCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    facebookEditEditText.setEnabled(b);
                    if (!b) facebookEditEditText.setText("");
                }
            });
            instagramEditCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    instagramEditEditText.setEnabled(b);
                    if (!b) instagramEditEditText.setText("");
                }
            });
            twitterEditCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    twitterEditEditText.setEnabled(b);
                    if (!b) twitterEditEditText.setText("");
                }
            });
            webEditCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    webEditEditText.setEnabled(b);
                    if (!b) webEditEditText.setText("");
                }
            });
        }

        private List<SocialMedia> saveSocialMedia() {
            if (checkData()) {
                Toast.makeText(getContext(), "Desmarque las opciones vacías", Toast.LENGTH_SHORT).show();
                return null;
            }

            List<SocialMedia> socialMediaList = new ArrayList<>();
            //esta lista no existe, se sutituye por una lista que recibe el dialog
            //no se agregan nuevos objetos ("add") sino va a ser set in position i
            //tal vez sea bueno quitar los IFs y siempre mandar los cuatro valores aunque estén vacíos, de igual manera recibir 4 valores siempre
            if (facebookEditCheckBox.isChecked())
                socialMediaList.add(setSocialMediaToCompanyModel(facebookEditCheckBox, facebookEditEditText, 0));
            if (instagramEditCheckBox.isChecked())
                socialMediaList.add(setSocialMediaToCompanyModel(instagramEditCheckBox, instagramEditEditText, 1));
            if (twitterEditCheckBox.isChecked())
                socialMediaList.add(setSocialMediaToCompanyModel(twitterEditCheckBox, twitterEditEditText, 2));
            if (webEditCheckBox.isChecked())
                socialMediaList.add(setSocialMediaToCompanyModel(webEditCheckBox, webEditEditText, 3));
            /*presenter.updateSocialMedia(AdminActivity.adminModel.getBusiness().getId(),facebookEditEditText.getText().toString(),
                    instagramEditEditText.getText().toString(),
                    twitterEditEditText.getText().toString(),
                    webEditEditText.getText().toString());*/
            return socialMediaList;
        }

        private List<CheckBox> checkBoxList;
        private List<EditText> editTextList;

        private void fillViewsList() {
            checkBoxList = new ArrayList<>();
            checkBoxList.add(facebookEditCheckBox);
            checkBoxList.add(instagramEditCheckBox);
            checkBoxList.add(twitterEditCheckBox);
            checkBoxList.add(webEditCheckBox);

            editTextList = new ArrayList<>();
            editTextList.add(facebookEditEditText);
            editTextList.add(instagramEditEditText);
            editTextList.add(twitterEditEditText);
            editTextList.add(webEditEditText);
        }

        private void fillSocialMedia() {
            setNullFields();
            for (SocialMedia socialMedia : socialMediaList) {
                switch (socialMedia.getSocialmedia_id()) {
                    case 0:
                        setSocialMedia(facebookEditCheckBox, facebookEditEditText, String.valueOf(socialMedia.getCode()));
                        break;
                    case 1:
                        setSocialMedia(instagramEditCheckBox, instagramEditEditText, String.valueOf(socialMedia.getCode()));
                        break;
                    case 2:
                        setSocialMedia(twitterEditCheckBox, twitterEditEditText, String.valueOf(socialMedia.getCode()));
                        break;
                    case 3:
                        setSocialMedia(webEditCheckBox, webEditEditText, String.valueOf(socialMedia.getCode()));
                        break;
                }
            }
        }

        private SocialMedia setSocialMediaToCompanyModel(CheckBox c, EditText e, int i) {
            SocialMedia sm = new SocialMedia();
            if (c.isChecked()) {
                sm.setSocialmedia_id(i);
                sm.setCode(e.getText().toString());
            }
            return sm;
        }

        private boolean checkData() {
            for (int i = 0; i < 3; i++) {
                if (checkBoxList.get(i).isChecked() && editTextList.get(i).getText().toString().equals(""))
                    return true;
            }
            return false;
        }

        private void setNullFields() {
            facebookEditEditText.setText("");
            facebookEditCheckBox.setChecked(false);

            instagramEditEditText.setText("");
            instagramEditCheckBox.setChecked(false);

            twitterEditEditText.setText("");
            twitterEditCheckBox.setChecked(false);

            webEditEditText.setText("");
            webEditCheckBox.setChecked(false);
        }

        private void setSocialMedia(CheckBox c, EditText t, String url) {
            c.setChecked(true);
            t.setText(url);
            t.setEnabled(true);
        }
    }

    private class ScheduleClass {

        private Calendar calendar = Calendar.getInstance();
        private int hour = calendar.get(Calendar.HOUR_OF_DAY);
        private int minute = calendar.get(Calendar.MINUTE);

        private View view;

        public ScheduleClass(List<Schedule> scheduleList, View view) {
            this.view = view;
            listeners();
            fillSchedule(scheduleList);
            fillTextViewList();
        }

        private void listeners() {
            informationScheduleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new InformationDialog(getContext(),
                            getString(R.string.information_schedule),
                            null, informationScheduleTextView, null).createMenuInformation();
                }
            });
            startMondayTextView.setOnClickListener(onClickListener);
            endMondayTextView.setOnClickListener(onClickListener);
            startTuesdayTextView.setOnClickListener(onClickListener);
            endTuesdayTextView.setOnClickListener(onClickListener);
            startWednesdayTextView.setOnClickListener(onClickListener);
            endWednesdayTextView.setOnClickListener(onClickListener);
            startThursdayTextView.setOnClickListener(onClickListener);
            endThursdayTextView.setOnClickListener(onClickListener);
            startFridayTextView.setOnClickListener(onClickListener);
            endFridayTextView.setOnClickListener(onClickListener);
            startSaturdayTextView.setOnClickListener(onClickListener);
            endSaturdayTextView.setOnClickListener(onClickListener);
            startSundayTextView.setOnClickListener(onClickListener);
            endSundayTextView.setOnClickListener(onClickListener);
        }

        private List<Schedule> saveSchedule() {
            List<Schedule> scheduleListFinal = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (!getTextFromSchedule(i, 0).equals("")) {
                    scheduleListFinal.add(new Schedule(i, getTextFromSchedule(i, 0),
                            getTextFromSchedule(i, 1)));
                } else {
                    //scheduleListFinal.add(new Schedule(i, "", ""));
                    scheduleListFinal.add(null);
                }
            }
            return scheduleListFinal.stream().filter(Objects::nonNull).collect(Collectors.toList());
        }

        private String getTextFromSchedule(int day, int type) {
            for (int i = 0; i < 7; i++) {
                if (i == day) {
                    return (type == 0) ?
                            textViewStartList.get(i).getText().toString() :
                            textViewEndList.get(i).getText().toString();
                }
            }
            return "";
        }

        private void openPicker(final View v) {
            final TextView t = this.view.findViewById(v.getId());
            if (!t.getText().equals("")) {
                String[] parts = t.getText().toString().split(":");
                hour = Integer.parseInt(parts[0]);
                minute = Integer.parseInt(parts[1]);
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    if (i1 < 10) t.setText(i + ":0" + i1);
                    else t.setText(i + ":" + i1);
                }
            }, hour, minute, true);
            timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Eliminar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        t.setText("");
                    }
                }
            });
            timePickerDialog.show();
        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.startMondayTextView:
                    case R.id.endMondayTextView:
                    case R.id.startTuesdayTextView:
                    case R.id.endTuesdayTextView:
                    case R.id.startWednesdayTextView:
                    case R.id.endWednesdayTextView:
                    case R.id.startThursdayTextView:
                    case R.id.endThursdayTextView:
                    case R.id.startFridayTextView:
                    case R.id.endFridayTextView:
                    case R.id.startSaturdayTextView:
                    case R.id.endSaturdayTextView:
                    case R.id.startSundayTextView:
                    case R.id.endSundayTextView:
                        openPicker(v);
                        break;
                }

            }
        };

        private void fillSchedule(List<Schedule> list) {
            for (Schedule sch : list) {
                if (sch.getDay() == 0) {
                    startMondayTextView.setText(String.valueOf(sch.getStart()));
                    endMondayTextView.setText(String.valueOf(sch.getEnd()));
                } else if (sch.getDay() == 1) {
                    startTuesdayTextView.setText(String.valueOf(sch.getStart()));
                    endTuesdayTextView.setText(String.valueOf(sch.getEnd()));
                } else if (sch.getDay() == 2) {
                    startWednesdayTextView.setText(String.valueOf(sch.getStart()));
                    endWednesdayTextView.setText(String.valueOf(sch.getEnd()));
                } else if (sch.getDay() == 3) {
                    startThursdayTextView.setText(String.valueOf(sch.getStart()));
                    endThursdayTextView.setText(String.valueOf(sch.getEnd()));
                } else if (sch.getDay() == 4) {
                    startFridayTextView.setText(String.valueOf(sch.getStart()));
                    endFridayTextView.setText(String.valueOf(sch.getEnd()));
                } else if (sch.getDay() == 5) {
                    startSaturdayTextView.setText(sch.getStart());
                    endSaturdayTextView.setText(String.valueOf(sch.getEnd()));
                } else if (sch.getDay() == 6) {
                    startSundayTextView.setText(sch.getStart());
                    endSundayTextView.setText(String.valueOf(sch.getEnd()));
                }

            }

        }

        private List<TextView> textViewStartList;
        private List<TextView> textViewEndList;

        private void fillTextViewList() {
            textViewStartList = new ArrayList<>();
            textViewStartList.add(startMondayTextView);
            textViewStartList.add(startTuesdayTextView);
            textViewStartList.add(startWednesdayTextView);
            textViewStartList.add(startThursdayTextView);
            textViewStartList.add(startFridayTextView);
            textViewStartList.add(startSaturdayTextView);
            textViewStartList.add(startSundayTextView);

            textViewEndList = new ArrayList<>();
            textViewEndList.add(endMondayTextView);
            textViewEndList.add(endTuesdayTextView);
            textViewEndList.add(endWednesdayTextView);
            textViewEndList.add(endThursdayTextView);
            textViewEndList.add(endFridayTextView);
            textViewEndList.add(endSaturdayTextView);
            textViewEndList.add(endSundayTextView);
        }
    }
}