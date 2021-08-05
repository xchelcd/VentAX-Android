package com.idax.ventax.Adapter.TutorialCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.Entity.User;
import com.idax.ventax.R;
import com.idax.ventax.ViewHolder.TutorialViewHolder;

import java.util.List;

import static com.idax.ventax.Extra.Constansts.AFFILIATE;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialViewHolder> {

    private List<Data> dataList;
    private Context context;
    private User user;
    public TutorialAdapter(Context context, User user){
        dataList = Data.getDataList(context);
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutorial, parent, false);

        return new TutorialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, int position) {
        if (position != 0) holder.getCoverTutorialImageView().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //else holder.getCoverTutorialImageView().setBackground(context.getResources().getDrawable(R.drawable.style_popup));
        if (dataList.get(position).getCover() == 0) holder.getCoverTutorialImageView().setVisibility(View.GONE);
        holder.getPageTutorialLinearLayout().setBackground(context.getDrawable((dataList.get(position).getAffiliate() == AFFILIATE)? R.drawable.style_outline_blue: R.drawable.style_outline_fucsia));
        holder.getTitleTutorialTextView().setText(dataList.get(position).getTitle());
        holder.getCoverTutorialImageView().setImageResource(dataList.get(position).getCover());
        holder.getTextTutorialTextView().setText(dataList.get(position).getText());
        holder.getExtraTextTutorialTextView().setText(dataList.get(position).getExtraText());
        try{holder.getTutorialLinearLayout().addView(dataList.get(position).getView());}catch (Exception ignored){}
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
