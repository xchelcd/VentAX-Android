package com.idax.ventax.Fragment.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.idax.ventax.Activity.Menu.MenuUpdateObjects;
import com.idax.ventax.Activity.RegisterCompany.RegisterActivity;
import com.idax.ventax.DataPersistence.DataPersistence;
import com.idax.ventax.Dialog.InformationDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.User;
import com.idax.ventax.R;

import java.text.MessageFormat;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.idax.ventax.DataPersistence.DataPersistence.enrollCompanyDataPersistence;
import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;
import static com.idax.ventax.Extra.Constansts.INTENT_USER;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_PROFILE_FRAGMENT;
import static com.idax.ventax.Extra.Constansts.RESULT_CODE_GOT_TO_MENU;
import static com.idax.ventax.Extra.Constansts.SHARD_PREFERENCES_USER;

public class ProfileFragment extends Fragment {

    private User user;
    private Company company;
    private MenuUpdateObjects updateObjects;

    private Button startBusinessButton;
    private TextView fullNameTextView;
    private TextView emailTextView;
    private TextView fullNumberPhoneTextView;
    private TextView ageTextView;
    private TextView genderTextView;

    private TextView affiliateTextView;
    private TextView dateAffiliateTextView;

    private Intent intent;

    private int request = 0;

    private TextView nameFragmentTextView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PROFILE_FRAGMENT:
                if (resultCode == RESULT_CODE_GOT_TO_MENU) {
                    //todo crear notificación para avisar que en breve se dará de alta su empresa
                    getSlidingRootNav(null).openMenu(true);
                    Toast.makeText(getContext(), "Registro exitoso, en breve se le notificará su aprobación", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().get(BUNDLE_USER);
            company = (Company) getArguments().get(BUNDLE_COMPANY);
            updateObjects = (MenuUpdateObjects) getArguments().get(BUNDLE_INTERFACE_OBJ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        inits(view);
        setData((LinearLayout) view.findViewById(R.id.svLinearLayout));
        listeners();
        return view;
    }

    private void setData(LinearLayout v) {
        //conditions
        if (user.getAffiliate() == AFFILIATE) {
            v.removeView(startBusinessButton);
            affiliateTextView.setCompoundDrawables(null, null, null, null);
        }

        //user data
        fullNameTextView.setText(MessageFormat.format("{0} {1}", user.getName(), user.getLastName()));
        fullNumberPhoneTextView.setText(MessageFormat.format("{0} {1}", user.getExt(), user.getPhone()));
        emailTextView.setText((user.getEmail() == null) ? getString(R.string.no_especificado) : (user.getEmail().isEmpty()) ? getString(R.string.no_especificado) : user.getEmail());
        ageTextView.setText((user.getAge() == 0) ? getString(R.string.no_especificado) : String.valueOf(user.getAge()));
        genderTextView.setText((new Random().nextInt(10) > 4) ? "ALIEN" : "NO ALIEN");
        affiliateTextView.setText((user.getAffiliate() == AFFILIATE) ? getString(R.string.affiliate) : getString(R.string.nulo));
        dateAffiliateTextView.setText((user.getAffiliate() == AFFILIATE) ? user.getStartDate() : getString(R.string.nulo));
    }

    private void listeners() {
        startBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enrollCompanyDataPersistence.getStateRequestEnroll() && request < 20) {
                    request++;
                    Toast.makeText(getContext(), "Solicitud en proceso", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(getContext(), RegisterActivity.class);
                intent.putExtra(INTENT_USER, user);
                startActivityForResult(intent, REQUEST_CODE_PROFILE_FRAGMENT);
            }
        });
        affiliateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getAffiliate() != AFFILIATE)
                    new InformationDialog(getContext(),
                            getString(R.string.information_affiliate),
                            null, affiliateTextView, null).createMenuInformation();
            }
        });
        nameFragmentTextView.setOnClickListener(v->{
            getSlidingRootNav(null).openMenu(true);
        });
    }

    private void inits(ViewGroup v) {
        enrollCompanyDataPersistence = new DataPersistence(getContext().getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE));
        startBusinessButton = v.findViewById(R.id.startBusinessButton);

        fullNameTextView = v.findViewById(R.id.fullNameTextView);
        emailTextView = v.findViewById(R.id.emailTextView);
        fullNumberPhoneTextView = v.findViewById(R.id.fullNumberPhoneTextView);
        ageTextView = v.findViewById(R.id.ageTextView);
        genderTextView = v.findViewById(R.id.genderTextView);

        affiliateTextView = v.findViewById(R.id.affiliateTextView);
        dateAffiliateTextView = v.findViewById(R.id.dateAffiliateTextView);

        nameFragmentTextView = v.findViewById(R.id.nameFragmentTextView);

    }
}
