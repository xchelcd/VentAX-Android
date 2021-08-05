package com.idax.ventax.Drawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;

public class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private DrawerAdapter drawerAdapter;

    public DrawerViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        drawerAdapter.setSelected(getAdapterPosition());
    }

    public void setDrawerAdapter(DrawerAdapter drawerAdapter) {
        this.drawerAdapter = drawerAdapter;
    }
}
