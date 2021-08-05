package com.idax.ventax.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.idax.ventax.R;

import static com.idax.ventax.Extra.Constansts.POSITION_TOP;

public class InformationDialog {

    private Context context;
    private Button button;
    private TextView textView;
    private ImageButton imageButton;
    private String message;

    public InformationDialog(Context context, String message, Button button, TextView textView, ImageButton imageButton) {
        this.context = context;
        this.button = button;
        this.textView = textView;
        this.imageButton = imageButton;
        this.message = message;
    }

    public void createMenuInformation() {
        //View item = (button == null) ? (imageButton == null) ? (imageView == null) ? textView : imageView : imageButton : button;
        View item = (button == null) ? (imageButton == null) ? textView : imageButton : button;
        int[] location = new int[2];
        item.getLocationOnScreen(location);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_information, null, false);
        textView = view.findViewById(R.id.informationTextView);
        textView.setText(message);

        final PopupWindow popUp = new PopupWindow(view,
                textView.getLayoutParams().width,
                textView.getLayoutParams().height, false);
        popUp.setElevation(10);
        popUp.setTouchable(true);
        popUp.setFocusable(true);
        popUp.setOutsideTouchable(true);
        popUp.showAtLocation(item, Gravity.NO_GRAVITY,
                location[0] + item.getWidth() / 3,
                location[1] + item.getHeight() + 2);
    }
}
