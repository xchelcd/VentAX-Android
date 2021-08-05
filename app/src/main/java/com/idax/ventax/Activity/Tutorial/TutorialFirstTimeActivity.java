package com.idax.ventax.Activity.Tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.idax.ventax.Adapter.TutorialCompany.TutorialAdapter;
import com.idax.ventax.Adapter.TutorialCompany.TutorialFirstTimeAdapter;
import com.idax.ventax.Entity.User;
import com.idax.ventax.R;

import static android.content.Intent.EXTRA_USER;
import static com.idax.ventax.DataPersistence.DataPersistence.isFirstTimeDataPersistence;
import static com.idax.ventax.Extra.Constansts.RESULT_CODE_GO_TO_TUTORIAL;

public class TutorialFirstTimeActivity extends AppCompatActivity {

    private TutorialFirstTimeAdapter tutorialAdapter;
    private ViewPager2 tutorialViewPager;

    private Button nextButton;
    private Button backButton;
    private Button omitButton;

    private int index = 0;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tutorial_first_time);
        inits();
        listeners();
        setAdapter();

    }

    private void listeners() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    setResult(RESULT_CODE_GO_TO_TUTORIAL);
                    onBackPressed();
                    //todo go to login activity
                    return;
                }
                tutorialViewPager.setCurrentItem(++index, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorialViewPager.setCurrentItem((index == 0) ? 0 : --index, true);
            }
        });
        omitButton.setOnClickListener(v -> {
            //todo go to login activity
        });
        tutorialViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 3) {
                    nextButton.setText("Finalizar");
                    flag = true;
                } else {
                    nextButton.setText("Siguiente →");
                    flag = false;
                }
                index = position;
            }
        });
    }

    private void inits() {
        tutorialAdapter = new TutorialFirstTimeAdapter(getApplicationContext());
        tutorialViewPager = findViewById(R.id.tutorialViewPager);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        omitButton = findViewById(R.id.omitButton);
    }

    private void setAdapter() {
        tutorialViewPager.setClipToPadding(false);
        tutorialViewPager.setClipChildren(false);
        tutorialViewPager.setOffscreenPageLimit(2);
        tutorialViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        tutorialViewPager.setAdapter(tutorialAdapter);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
            }
        });
        tutorialViewPager.setPageTransformer(transformer);
    }
}