package com.idax.ventax.Activity.EntrepreneurExtra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.FAQ;
import com.idax.ventax.Fragment.FAQ.FAQFragment;
import com.idax.ventax.R;

import java.io.Serializable;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_FAQ_LIST;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INT_FOR_FAQ;
import static com.idax.ventax.Extra.Constansts.INTENT_COMPANY;
import static com.idax.ventax.Extra.Constansts.INTENT_FAQ_LIST;
import static com.idax.ventax.Extra.Constansts.NOT_AFFILIATE;

public class EntrepreneurExtraActivity extends AppCompatActivity {

    private List<FAQ> faqList;
    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_entrepreneur_extra);
        inits();
        setFragment();
    }

    private void inits() {
        faqList = (List<FAQ>) getIntent().getExtras().get(INTENT_FAQ_LIST);
        company = (Company) getIntent().getExtras().get(INTENT_COMPANY);
    }

    private void setFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        FAQFragment fragment = new FAQFragment();
        bundle.putSerializable(BUNDLE_FAQ_LIST, (Serializable) faqList);
        bundle.putInt(BUNDLE_INT_FOR_FAQ, NOT_AFFILIATE);
        bundle.putSerializable(BUNDLE_COMPANY, company);
        fragment.setArguments(bundle);
        transaction.replace(R.id.containerF, fragment);
        transaction.commit();
    }
}