package com.idax.ventax.Drawer;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerViewHolder> {

    private List<DrawerItem> items;
    private Map<Class<? extends DrawerItem>, Integer> viewTypes;
    private SparseArray<DrawerItem> holderFactories;
    private OnItemSelectedListener listener;


    public DrawerAdapter(List<DrawerItem> items) {
        this.items = items;
        viewTypes = new HashMap<>();
        holderFactories = new SparseArray<>();
        processViewTypes();
    }

    private void processViewTypes() {
        int type = 0;
        for (DrawerItem item : items) {
            if(!viewTypes.containsKey(item.getClass())){
                viewTypes.put(item.getClass(), type);
                holderFactories.put(type, item);
                type++;
            }
        }
    }

    @NonNull
    @Override
    public DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DrawerViewHolder holder = holderFactories.get(viewType).createViewHolder(parent);
        holder.setDrawerAdapter(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerViewHolder holder, int position) {
        items.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }

    public void setSelected(int position){
        DrawerItem newChecked = items.get(position);
        if(!newChecked.isSelectable()){
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            DrawerItem item = items.get(i);
            if (item.isChecked){
                item.setChecked(false);
                notifyItemChanged(i);
                break;
            }
        }
        newChecked.setChecked(true);
        notifyItemChanged(position);
        if (listener != null){
            listener.onItemSelected(position);
        }
    }

    public void setListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int position);
    }
}
