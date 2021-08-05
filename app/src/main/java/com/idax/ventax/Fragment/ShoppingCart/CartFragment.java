package com.idax.ventax.Fragment.ShoppingCart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idax.ventax.Activity.Order.OrderUActivity;
import com.idax.ventax.Adapter.ShoppingCart.CartBusinessAdapter;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;
import com.idax.ventax.RoomDB.AppDataBase.AppDataBase;

import java.io.Serializable;
import java.util.List;

import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;
import static com.idax.ventax.Extra.Constansts.INTENT_ORDER_MODEL_LIST;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_SHOPPING_CART;

public class CartFragment extends Fragment implements CartView {

    private User user;

    private RecyclerView recyclerView;

    private ImageView orderUImageView;

    private CartPresenter presenter;

    private LoadingDialog dialog;

    private Intent intent;

    private TextView noRegisterTextView;

    private TextView titleTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().get(BUNDLE_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cart, null, false);
        inits(view);
        listeners();
        setAdapter();
        return view;
    }

    private void setAdapter() {
        CartBusinessAdapter adapter = new CartBusinessAdapter(this, getContext(), getOrderList(), user.getId(), getParentFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<OrderSQLite> getOrderList() {
        List<OrderSQLite> orderSQLiteList = AppDataBase.getAppDataBase(getContext()).orderDao().getAllBusinessNames();
        noRegisterTextView.setVisibility(orderSQLiteList.size() == 0 ? View.VISIBLE : View.GONE);
        return orderSQLiteList;
    }

    private void listeners() {
        orderUImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.GetAllUserOrdersByUserId(user.getId());
            }
        });
        titleTextView.setOnClickListener(v->{
            getSlidingRootNav(null).openMenu(true);
        });
    }

    private void inits(View v) {
        titleTextView = v.findViewById(R.id.titleTextView);
        noRegisterTextView = v.findViewById(R.id.noRegisterTextView);

        orderUImageView = v.findViewById(R.id.orderUImageView);
        recyclerView = v.findViewById(R.id.businessNameRecyclereView);
        presenter = new CartPresenter(this);
        dialog = new LoadingDialog(getContext());
    }

    @Override
    public void onSuccessResponse(List<OrderModel> orderModelList) {
        intent = new Intent(getActivity(), OrderUActivity.class);
        intent.putExtra(INTENT_ORDER_MODEL_LIST, (Serializable) orderModelList);
        startActivityForResult(intent, REQUEST_CODE_SHOPPING_CART);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    public void onErrorResponse(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnOrderRequestSuccess(List<OrderSQLite> orderSQLiteList) {
        //todo delete item from recyclerView
        AppDataBase.getAppDataBase(getContext()).orderDao().deleteOrders(orderSQLiteList);
        setAdapter();
    }

    @Override
    public void showProgressBar(String message) {
        dialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        dialog.closeLoadingDialog();
    }
}