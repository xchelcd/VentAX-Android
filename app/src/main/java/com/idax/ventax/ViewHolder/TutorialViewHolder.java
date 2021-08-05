package com.idax.ventax.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.scales.Linear;
import com.idax.ventax.R;

public class TutorialViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout pageTutorialLinearLayout;
    private LinearLayout tutorialLinearLayout;
    private TextView titleTutorialTextView;
    private ImageView coverTutorialImageView;
    private TextView textTutorialTextView;
    private TextView extraTextTutorialTextView;

    public TutorialViewHolder(@NonNull View v) {
        super(v);
        pageTutorialLinearLayout = v.findViewById(R.id.pageTutorialLinearLayout);
        tutorialLinearLayout = v.findViewById(R.id.tutorialLinearLayout);
        titleTutorialTextView = v.findViewById(R.id.titleTutorialTextView);
        coverTutorialImageView = v.findViewById(R.id.coverTutorialImageView);
        textTutorialTextView = v.findViewById(R.id.textTutorialTextView);
        extraTextTutorialTextView = v.findViewById(R.id.extraTextTutorialTextView);
    }

    public LinearLayout getPageTutorialLinearLayout() {
        return pageTutorialLinearLayout;
    }

    public void setPageTutorialLinearLayout(LinearLayout pageTutorialLinearLayout) {
        this.pageTutorialLinearLayout = pageTutorialLinearLayout;
    }

    public LinearLayout getTutorialLinearLayout() {
        return tutorialLinearLayout;
    }

    public TextView getTitleTutorialTextView() {
        return titleTutorialTextView;
    }

    public void setTitleTutorialTextView(TextView titleTutorialTextView) {
        this.titleTutorialTextView = titleTutorialTextView;
    }

    public ImageView getCoverTutorialImageView() {
        return coverTutorialImageView;
    }

    public void setCoverTutorialImageView(ImageView coverTutorialImageView) {
        this.coverTutorialImageView = coverTutorialImageView;
    }

    public TextView getTextTutorialTextView() {
        return textTutorialTextView;
    }

    public void setTextTutorialTextView(TextView textTutorialTextView) {
        this.textTutorialTextView = textTutorialTextView;
    }

    public TextView getExtraTextTutorialTextView() {
        return extraTextTutorialTextView;
    }

    public void setExtraTextTutorialTextView(TextView extraTextTutorialTextView) {
        this.extraTextTutorialTextView = extraTextTutorialTextView;
    }
}
