package com.idax.ventax.Adapter.ShoppingCart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.R;
import com.idax.ventax.RoomDB.AppDataBase.AppDataBase;
import com.idax.ventax.RoomDB.DAO.OrderDao;
import com.idax.ventax.ViewHolder.ShoppingCart.CartProductViewHolder;

import java.text.MessageFormat;
import java.util.List;

import static com.idax.ventax.Extra.Util.formatDecimal;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductViewHolder> {

    private List<OrderSQLite> orderSQLiteList;
    private Context context;
    private int userId;

    private OrderDao orderDao;

    public CartProductAdapter(Context context, List<OrderSQLite> orderSQLiteList, int userId) {
        this.context = context;
        this.orderSQLiteList = orderSQLiteList;
        this.userId = userId;
        orderDao = AppDataBase.getAppDataBase(context).orderDao();
    }

    @NonNull
    @Override
    public CartProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_orders, parent, false);
        return new CartProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductViewHolder holder, int position) {
        holder.getProductNameTextView().setText(
                MessageFormat.format("({1}) {0}", orderSQLiteList.get(position).getProductName(), orderSQLiteList.get(position).getQty()));
        holder.getPriceTextView().setText(formatDecimal(orderSQLiteList.get(position).getQty() * orderSQLiteList.get(position).getPrice()));
        holder.getDeleteProductImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "DELETE->" + orderSQLiteList.get(position).getProductName(), Toast.LENGTH_SHORT).show();
                orderDao.deleteAllByProductId(orderSQLiteList.get(position).getProductId());
                orderSQLiteList.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.getEditProductImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "EDIT->" + orderSQLiteList.get(position).getProductName(), Toast.LENGTH_SHORT).show();
                if (holder.getQtyEditText().getVisibility() == View.GONE) {
                    holder.getQtyEditText().setText(String.valueOf(orderSQLiteList.get(position).getQty()));
                    holder.getQtyEditText().setVisibility(View.VISIBLE);
                } else {
                    holder.getQtyEditText().setVisibility(View.GONE);
                }
            }
        });
        holder.getQtyEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holder.getProductNameTextView().setText(
                        MessageFormat.format("({1}) {0}", orderSQLiteList.get(position).getProductName(), s.toString()));
                int qty;
                try {
                    qty = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    qty = 0;
                }
                int id = orderSQLiteList.get(position).getProductId();
                AppDataBase.getAppDataBase(context).orderDao().upadteQty(id, qty);
                holder.getPriceTextView().setText(formatDecimal(qty * orderSQLiteList.get(position).getPrice()));


            }
        });
    }

    @Override
    public int getItemCount() {
        return orderSQLiteList.size();
    }
}
