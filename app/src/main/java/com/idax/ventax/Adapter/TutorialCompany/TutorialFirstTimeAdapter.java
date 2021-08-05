package com.idax.ventax.Adapter.TutorialCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;
import com.idax.ventax.ViewHolder.TutorialFirstTimeViewHolder;
import com.idax.ventax.ViewHolder.TutorialViewHolder;

import java.util.List;

public class TutorialFirstTimeAdapter extends RecyclerView.Adapter<TutorialFirstTimeViewHolder> {

    private List<DataFirstTime> dataFirstTimeList;
    private Context context;

    public TutorialFirstTimeAdapter(Context context) {
        this.dataFirstTimeList = DataFirstTime.getDataList(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TutorialFirstTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutorial_first_time, parent, false);

        return new TutorialFirstTimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialFirstTimeViewHolder holder, int position) {
        //todo set data int layout
    }

    @Override
    public int getItemCount() {
        return dataFirstTimeList.size();
    }
}
