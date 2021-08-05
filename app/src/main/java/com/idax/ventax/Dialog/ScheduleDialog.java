package com.idax.ventax.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.idax.ventax.Entity.Schedule;
import com.idax.ventax.R;

import java.util.List;

import static com.idax.ventax.Extra.Constansts.POSITION_TOP;
import static com.idax.ventax.Extra.Util.getDay;

public class ScheduleDialog {

    private Context context;
    private Button button;
    private TextView textView;
    private ImageButton imageButton;
    private List<Schedule> scheduleList;

    private int position;
    private String background;

    public ScheduleDialog(Context context, List<Schedule> scheduleList, Button button, TextView textView, ImageButton imageButton, String background, int position) {
        this.context = context;
        this.button = button;
        this.textView = textView;
        this.imageButton = imageButton;
        this.scheduleList = scheduleList;

        this.background = background;
        this.position = position;
    }

    public void createMenuInformation() {
        View item = (button == null) ? (imageButton == null) ? textView : imageButton : button;
        int[] location = new int[2];
        item.getLocationOnScreen(location);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_schedule, null, false);
        LinearLayout linearLayoutInformation = view.findViewById(R.id.linearLayoutInformation);
        CardView cardView = view.findViewById(R.id.cardViewInformation);
        cardView.setCardBackgroundColor((Color.parseColor(background)));
        int[] size = fillLinearLayout(linearLayoutInformation);

        final PopupWindow popUp = new PopupWindow(view,
                size[0],
                size[1], false);
        popUp.setElevation(10);
        popUp.setTouchable(true);
        popUp.setFocusable(true);
        popUp.setOutsideTouchable(true);
        int y = (position == POSITION_TOP) ? (location[1] - (item.getHeight() * 4)) : location[1] + item.getHeight() + 2;
        popUp.showAtLocation(item, Gravity.NO_GRAVITY,
                location[0] + item.getWidth() / 3,
                y);
    }

    private int[] fillLinearLayout(LinearLayout linearLayout) {
        int width = 0;
        int height = 0;
        int i = 0;
        for (Schedule s : scheduleList) {
            TextView t = new TextView(context);
            t.setLayoutParams(textLayoutParams());
            t.setText(setSchedule(s, s.getDay()));
            t.setTextColor(context.getColor(R.color.blackColorVentax));
            t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t.setPadding(10, 5, 10, 5);
            LinearLayout l = new LinearLayout(context);
            l.setPadding(20, 0, 20, 0);
            l.setLayoutParams(linearLayoutParams());
            l.setBackgroundColor(context.getColor(R.color.grayColorVentaxSchedule));
            linearLayout.addView(t);
            if (i != 6) linearLayout.addView(l);
            width = t.getLayoutParams().width;
            height = t.getLayoutParams().height;
            i++;
        }
        return new int[]{width, height};
    }

    private StringBuffer setSchedule(Schedule s, int i) {
        return new StringBuffer().append(getDay(context, i)).append(": ").append(s.getStart().substring(0, 5)).append(" - ").append(s.getEnd().substring(0, 5));
    }

    private LinearLayout.LayoutParams textLayoutParams() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams linearLayoutParams() {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        p.setMargins(10, 0, 10, 0);
        return p;
    }
}
