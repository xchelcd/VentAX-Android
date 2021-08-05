package com.idax.ventax.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.idax.ventax.Entity.FAQ;
import com.idax.ventax.R;

import java.util.List;

import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Util.getNumOfFAQsByAccountType;

public class FAQAdapter extends BaseExpandableListAdapter{
    private List<FAQ> faqList;
    private Context context;
    private int userAffiliate;
    private int accountType;

    public FAQAdapter(Context context, List<FAQ> faqList, int userAffiliate, int accountType) {
        this.context = context;
        this.faqList = faqList;
        this.userAffiliate = userAffiliate;
        this.accountType = accountType;
    }

    //RECIBE EL NUMERO DE PREGUNTAS MÁXIMA QUE PUEDE HACCER PERO SOLO SE LLENAN EL NÚMERO DE PREGUNTAS QUE HA CREADO
    @Override
    public int getGroupCount() {
        return (userAffiliate != AFFILIATE) ? faqList.size() : getNumOfFAQsByAccountType(accountType);
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return faqList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return 0;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View v, ViewGroup viewGroup) {
        v = LayoutInflater.from(context).inflate(R.layout.item_faq_parent, viewGroup, false);
        TextView parentFAQTextView = v.findViewById(R.id.parentFAQTextView);
        try {
            parentFAQTextView.setText(faqList.get(i).getQuestion());
            if (userAffiliate == AFFILIATE)
                parentFAQTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_radio_button_checked_24, 0);
        } catch (Exception e) {
            parentFAQTextView.setText("Pregunta " + (i + 1));
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View v, ViewGroup viewGroup) {
        v = LayoutInflater.from(context).inflate(R.layout.item_faq_child, viewGroup, false);
        TextView childFAQTextView = v.findViewById(R.id.childFAQTextView);
        try {
            childFAQTextView.setText(faqList.get(i).getAnswer());
        } catch (Exception e) {
            childFAQTextView.setText("Respuesta " + (i + 1));
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
