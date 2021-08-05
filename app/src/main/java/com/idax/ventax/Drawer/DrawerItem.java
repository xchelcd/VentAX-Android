package com.idax.ventax.Drawer;

import android.view.ViewGroup;

public abstract class DrawerItem<T extends DrawerViewHolder>{

    protected boolean isChecked;
    protected boolean visible;
    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder);

    public DrawerItem<T>setChecked(boolean isChecked){
        this.isChecked = isChecked;
        return  this;
    }

    public DrawerItem<T>setVisible(boolean visible){
        this.visible = visible;
        return  this;
    }

    public boolean isSelectable(){
        return true;
    }
}
