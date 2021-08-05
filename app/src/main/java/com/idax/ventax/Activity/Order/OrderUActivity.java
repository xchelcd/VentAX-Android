package com.idax.ventax.Activity.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.idax.ventax.Adapter.OrderAdapter;
import com.idax.ventax.BottomSheet.Order.StepOrderBottomSheet;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.util.List;

import static com.idax.ventax.Extra.Constansts.AS_USER;
import static com.idax.ventax.Extra.Constansts.INTENT_ORDER_MODEL_LIST;
import static com.idax.ventax.Extra.Constansts.USER_VIEW;

public class OrderUActivity extends AppCompatActivity implements OrderUView {

    private OrderUPresenter presenter;

    private TextView titleTextView;
    private ImageButton backImageButton;
    private RecyclerView recyclereView;
    private List<OrderModel> orderModelList;

    private OrderAdapter adapter;

    private OrderModel orderModel;
    private int index;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_order_u);

        inits();
        listeners();
        setAdapter(orderModelList);
    }

    private void setAdapter(List<OrderModel> orderModelList) {
        adapter = new OrderAdapter(orderModelList, getApplicationContext(), AS_USER, presenter, getSupportFragmentManager());
        recyclereView.setLayoutManager(new LinearLayoutManager(this));
        recyclereView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(OrderUActivity.this,
                //        orderModelList.get(recyclereView.getChildAdapterPosition(v)).getOrderId() + "",
                //        Toast.LENGTH_SHORT).show();
                index = recyclereView.getChildAdapterPosition(v);
                orderModel = orderModelList.get(index);
                new StepOrderBottomSheet(null, orderModel, USER_VIEW, index).show(getSupportFragmentManager(), "StepOrderBottomSheet");
            }
        });
    }

    private void listeners() {
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void inits() {
        presenter = new OrderUPresenter(this);
        titleTextView = findViewById(R.id.titleTextView);
        recyclereView = findViewById(R.id.orderURecyclereView);
        orderModelList = (List<OrderModel>) getIntent().getSerializableExtra(INTENT_ORDER_MODEL_LIST);
        titleTextView.setText(getResources().getString(R.string.pedidos_realizados));
        backImageButton = findViewById(R.id.backImageButton);
        loadingDialog = new LoadingDialog(getApplicationContext());
    }

    @Override
    public void OnSuccessCancel(OrderModel orderModel, int position) {
        Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
        orderModelList.set(position, orderModel);
        adapter.notifyItemChanged(position);
//        orderModelList.remove(orderModel);
//        adapter.notifyItemRemoved(position);

    }

    @Override
    public void OnErrorCancel() {
        Toast.makeText(this, "Hubo un error, no se pudo cancelar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(String message) {
        loadingDialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        loadingDialog.closeLoadingDialog();
    }
}