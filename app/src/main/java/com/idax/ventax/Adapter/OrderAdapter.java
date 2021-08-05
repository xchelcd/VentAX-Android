package com.idax.ventax.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.Activity.Order.OrderUPresenter;
import com.idax.ventax.Dialog.Order.Cancel.CancelOrderDialog;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.OrderProduct;
import com.idax.ventax.R;
import com.idax.ventax.ViewHolder.OrderViewHolder;

import java.text.MessageFormat;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.ACEPTED;
import static com.idax.ventax.Extra.Constansts.AS_USER;
import static com.idax.ventax.Extra.Constansts.CANCELED;
import static com.idax.ventax.Extra.Constansts.FINISHED;
import static com.idax.ventax.Extra.Constansts.PENDING;
import static com.idax.ventax.Extra.Constansts.PROCCESS;
import static com.idax.ventax.Extra.Constansts.CANCEL_TYPE_CLIENT;
import static com.idax.ventax.Extra.Util.formatDecimal;
import static com.idax.ventax.Extra.Util.getStateOrder;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> implements View.OnClickListener {

    private List<OrderModel> orderList;
    private Context context;
    private int accountType;
    private OrderUPresenter presenter;

    private FragmentManager fragmentManager;

    private int id;


    public OrderAdapter(List<OrderModel> orderList, Context context, int accountType, OrderUPresenter presenter, FragmentManager fragmentManager) {
        this.orderList = orderList;
        this.context = context;
        this.accountType = accountType;
        this.presenter = presenter;
        this.fragmentManager = fragmentManager;
    }

    private TableRow tableRow;
    private TextView qtyOrderProductTextView;
    private TextView nameOrderProductTextView;
    private TextView totalOrderProductTextView;

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(v);
        v.setOnClickListener(this);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.getCancelOrderUButton().setVisibility((accountType == AS_USER) ? View.VISIBLE : View.GONE);
        holder.getCancelOrderUButton().setEnabled((accountType == AS_USER));
        OrderModel order = orderList.get(position);
        holder.getStateOrderTextView().setTextColor(
                context.getResources().getColor(
                        (order.getState() == FINISHED) ? R.color.finishedOrderColorVentax :
                                (order.getState() == PENDING) ? R.color.pendingOrderColorVentax :
                                        (order.getState() == PROCCESS) ? R.color.proccessOrderColorVentax :
                                                (order.getState() == ACEPTED) ? R.color.aceptedOrderColorVentax :
                                                R.color.canceledOrderColorVentax));
        holder.getOrderIdTextView().setText(MessageFormat.format("#{0}", order.getOrderId()));
        holder.getCompanyNameTextView().setText((accountType == AS_USER) ? order.getCompanyName() : "");
        holder.getDateTextView().setText(order.getDate().substring(0, 10));
        holder.getStateOrderTextView().setText(getStateOrder(context, order.getState()));
        holder.getTotalOrderTextView().setText(formatDecimal(order.getTotal() / 100));
        holder.getCancelOrderUButton().setOnClickListener(v -> {
            if (order.getState() != CANCELED)
                new CancelOrderDialog(order, presenter, CANCEL_TYPE_CLIENT, position).show(fragmentManager, "CancelOrderDialog");
            else Toast.makeText(context, "Orden previamente cancelada", Toast.LENGTH_SHORT).show();
        });
        //holder.getCancelOrderUButton().setOnClickListener(v ->
        //        new CancelOrderDialog(order, presenter, CANCEL_TYPE_CLIENT, position).show(fragmentManager, "CancelOrderDialog"));

        for (OrderProduct op : order.getOrderProductList()) {
            tableRow = (TableRow) LayoutInflater.from(context).inflate(R.layout.item_order_product, null, false);
            inits(tableRow);
            qtyOrderProductTextView.setText(MessageFormat.format("({0})", op.getQty()));
            nameOrderProductTextView.setText(op.getProduct());
            totalOrderProductTextView.setText(formatDecimal(op.getTotal() / 100));
            holder.getOrderItemsTableLayout().addView(tableRow);
        }
    }

    private void inits(TableRow tableRow) {
        qtyOrderProductTextView = tableRow.findViewById(R.id.qtyOrderProductTextView);
        nameOrderProductTextView = tableRow.findViewById(R.id.nameOrderProductTextView);
        totalOrderProductTextView = tableRow.findViewById(R.id.totalOrderProductTextView);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}
