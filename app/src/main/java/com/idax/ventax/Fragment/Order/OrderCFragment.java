package com.idax.ventax.Fragment.Order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.Adapter.OrderAdapter;
import com.idax.ventax.BottomSheet.Order.StepOrderBottomSheet;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.util.List;

import static com.idax.ventax.Extra.Constansts.AS_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;
import static com.idax.ventax.Extra.Constansts.COMPANY_VIEW;

public class OrderCFragment extends Fragment implements OrderCView {

    private Company company;
    private User user;

    private LoadingDialog dialog;
    private OrderCPresenter presenter;

    private TextView titleTextView;
    private ImageButton backImageButton;
    private RecyclerView recyclereView;
    private List<OrderModel> orderModelList;


    private OrderAdapter adapter;
    private int index;

    private OrderCView view = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().get(BUNDLE_USER);
            company = (Company) getArguments().get(BUNDLE_COMPANY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_order_u, container, false);
        inits(view);
        presenter.GetAllOrdersByCompanyId(company.getId());
        listeners();
        return view;
    }

    private void listeners() {

    }

    private void inits(ViewGroup v) {
        titleTextView = v.findViewById(R.id.titleTextView);
        dialog = new LoadingDialog(getContext());
        presenter = new OrderCPresenter(this);
        recyclereView = v.findViewById(R.id.orderURecyclereView);
        titleTextView.setText(getContext().getResources().getString(R.string.pedidos_company));
        v.findViewById(R.id.backImageButton).setVisibility(View.GONE);
    }

    private OrderModel orderModel;

    private void setAdapter(List<OrderModel> orderModelList) {
        adapter = new OrderAdapter(orderModelList, getContext(), AS_COMPANY, null, getParentFragmentManager());
        recyclereView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclereView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = recyclereView.getChildAdapterPosition(v);
                orderModel = orderModelList.get(index);
                new StepOrderBottomSheet(view, orderModel, COMPANY_VIEW, index).show(getParentFragmentManager(), "StepOrderBottomSheet");
            }
        });
    }

    @Override
    public void onSuccessResponse(List<OrderModel> orderModelList) {
        this.orderModelList = orderModelList;
        setAdapter(orderModelList);
    }

    @Override
    public void onErrorResponse(String message) {

    }

    @Override
    public void StepChanged(int state, int orderId) {
        //orderModelList.stream().findAny().filter(x -> x.getOrderId() == orderId).get().setState(state);
        orderModel.setState(state);
        setAdapter(orderModelList);
    }

    @Override
    public void OnOrderCanceled(OrderModel orderModel, int position) {//todo aqui
        orderModelList.set(position, orderModel);
        adapter.notifyItemChanged(position);
        //orderModelList.remove(orderModel);
        //adapter.notifyItemRemoved(index);
        //adapter.notifyItemChanged(position, orderModel);
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
