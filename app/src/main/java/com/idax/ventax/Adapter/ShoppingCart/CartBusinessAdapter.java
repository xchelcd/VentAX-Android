package com.idax.ventax.Adapter.ShoppingCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.Dialog.Order.MultiProducts.ConfirmOrderDialog;
import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.Fragment.ShoppingCart.CartPresenter;
import com.idax.ventax.Fragment.ShoppingCart.CartView;
import com.idax.ventax.R;
import com.idax.ventax.RoomDB.AppDataBase.AppDataBase;
import com.idax.ventax.ViewHolder.ShoppingCart.CartBusinessViewHolder;

import java.util.List;

public class CartBusinessAdapter extends RecyclerView.Adapter<CartBusinessViewHolder> {

    private List<OrderSQLite> orderSQLiteList;
    private Context context;
    private int userId;
    FragmentManager fm;
    private CartProductAdapter adapter;

    private CartView cartView;

    public CartBusinessAdapter(CartView cartView, Context context, List<OrderSQLite> orderSQLiteList, int userId, FragmentManager fm) {
        this.userId = userId;
        this.orderSQLiteList = orderSQLiteList;
        this.context = context;
        this.fm = fm;
        this.cartView = cartView;
    }


    @NonNull
    @Override
    public CartBusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_business, parent, false);
        return new CartBusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartBusinessViewHolder holder, int position) {
        holder.getBusinessNameTextView().setText(orderSQLiteList.get(position).getCompanyName());
        holder.getGetAllProductsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+orderSQLiteList.get(position).getPrice(), Toast.LENGTH_SHORT).show();
                //new ConfirmOrder(getOrderList(orderSQLiteList.get(position).getCompanyId()), userId).show(fm, "ConfirmOrder");
                //AppDataBase.getAppDataBase(getContext()).orderDao().getAllBusinessNames()
                new ConfirmOrderDialog(cartView, getOrderList(AppDataBase.getAppDataBase(context).orderDao().getAllBusinessNames().get(position).getCompanyId()),
                        userId).show(fm, "ConfirmOrder");
            }
        });
        adapter = new CartProductAdapter(context, getOrderList(orderSQLiteList.get(position).getCompanyId()), userId);
        holder.getRecyclerView().setAdapter(adapter);
        holder.getRecyclerView().setLayoutManager(new LinearLayoutManager(context));
    }

    private List<OrderSQLite> getOrderList(int companyId) {
        List<OrderSQLite> o = AppDataBase.getAppDataBase(context).orderDao().getAllOrdersByCompany(companyId);
        return o;
    }

    @Override
    public int getItemCount() {
        return orderSQLiteList.size();
    }
}
