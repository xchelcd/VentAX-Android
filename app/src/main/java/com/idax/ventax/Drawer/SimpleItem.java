package com.idax.ventax.Drawer;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.idax.ventax.R;

public class SimpleItem extends DrawerItem<SimpleItem.ViewHolder> {

    private int selectedItemIconTint;
    private int selectedItemTextTint;

    private int normalItemIconTint;
    private int normalItemTextTint;

    private Drawable icon;
    private String title;

    public SimpleItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        holder.titleDrawerTextView.setText(title);
        holder.iconDrawerImageView.setImageDrawable(icon);

        holder.titleDrawerTextView.setTextColor(isChecked ? selectedItemTextTint : normalItemTextTint);
        holder.iconDrawerImageView.setColorFilter(isChecked ? selectedItemIconTint : normalItemIconTint);

        holder.itemView.setEnabled(visible);
        holder.itemView.setVisibility((visible) ? View.VISIBLE : View.INVISIBLE);
    }

    public SimpleItem withSelectedIconTint(int selectedItemIconTint) {
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }

    public SimpleItem withSelectedTextTint(int selectedItemTextTint) {
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

    public SimpleItem withIconTint(int normalItemIconTint) {
        this.normalItemIconTint = normalItemIconTint;
        return this;
    }

    public SimpleItem withTextTint(int normalItemTextTint) {
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }

    static class ViewHolder extends DrawerViewHolder {

        private ImageView iconDrawerImageView;
        private TextView titleDrawerTextView;

        public ViewHolder(@NonNull View v) {
            super(v);
            iconDrawerImageView = v.findViewById(R.id.iconDrawerImageView);
            titleDrawerTextView = v.findViewById(R.id.titleDrawerTextView);
        }
    }
}
