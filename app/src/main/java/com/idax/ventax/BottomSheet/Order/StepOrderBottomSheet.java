package com.idax.ventax.BottomSheet.Order;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.idax.ventax.Activity.Order.OrderUPresenter;
import com.idax.ventax.Activity.Order.OrderUView;
import com.idax.ventax.Dialog.InformationDialog;
import com.idax.ventax.Dialog.Order.Cancel.CancelOrderDialog;
import com.idax.ventax.Dialog.Order.Cancel.CancelOrderInterface;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.Fragment.Order.OrderCView;
import com.idax.ventax.R;
import com.shuhart.stepview.StepView;

import java.text.MessageFormat;
import java.util.Arrays;

import static com.idax.ventax.Extra.Constansts.CANCELED;
import static com.idax.ventax.Extra.Constansts.CANCEL_TYPE_COMPANY;
import static com.idax.ventax.Extra.Util.getStateDescription;
import static com.idax.ventax.Extra.Util.getStateOrder;

public class StepOrderBottomSheet extends BottomSheetDialogFragment implements StepOrderView, OrderUView {

    private StepView orderStepView;
    private OrderModel orderModel;

    private StepOrderPresenter presenter;
    private OrderCView view;

    private LoadingDialog loading;

    private Button nextOrderUButton;
    private Button cancelOrderUButton;
    private ImageButton contactImageButton;
    private ImageButton commentImageButton;
    private TextView nameTextView;
    private TextView descriptionStepTextView;
    private FrameLayout topMenuFrameLayout;
    private FrameLayout bottomMenuFrameLayout;

    private OrderUPresenter orderUPresenter;

    private Intent intent;

    private int state;
    private String comment;
    private boolean typeView;
    private boolean step = false;
    private int position;

    public StepOrderBottomSheet(OrderCView view, OrderModel orderModel, boolean typeView, int position) {
        this.view = view;
        this.orderModel = orderModel;
        state = orderModel.getState();
        comment = orderModel.getComment();
        //comment = "Me gustarpia que mi prodcuto sea de color verde y tenga unos destellos mamalones para que los pueda presumir con la banduki. igual el producto 3 que se ve en la iamgen peude tener dos dibujos?";
        this.typeView = typeView;
        step = state == 3;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_step_order, null, false);
        inits(v);

        listenters();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        bottomSheetDialog.show();
        return bottomSheetDialog;
    }

    private void setTextState(int step) {
        if (this.step) {
            nextOrderUButton.setText(getString(R.string.finalize_order));
            step = 3;
            state = 3;
        }
        descriptionStepTextView.setText(MessageFormat.format("{0}: {1}",
                getStateOrder(getContext(), step), getStateDescription(getContext(), step)));
    }

    private void listenters() {
        nextOrderUButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step) {
                    Toast.makeText(getContext(), "SIN FUNCIONALIDAD.\nSi deseas hacer uso de esta opción comunícate con idax a través de nuestro FB: /idax.mx", Toast.LENGTH_LONG).show();
                } else {
                    presenter.NextStepOrder(orderModel.getOrderId(), ++state);
                    //if (state < 2) orderStepView.go(++state, true);
                    //else {
                    //    orderStepView.go(3, true);
                    //    step = true;
                    //}
                    //setTextState(state);
                }
            }
        });
        cancelOrderUButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CancelOrderDialog(orderModel, orderUPresenter, CANCEL_TYPE_COMPANY, position).show(getParentFragmentManager(), "CancelOrderDialog");
            }
        });
        contactImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWhatsApp(orderModel.getPhone(), MessageFormat.format("Hola {0}! ", orderModel.getPersonaName()));
            }
        });
        commentImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment != null)
                    new InformationDialog(getContext(), comment, null, null, commentImageButton).createMenuInformation();
                else Toast.makeText(getContext(), "No hay comentarios.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToWhatsApp(String urlId, String msg) {
        intent = new Intent(Intent.ACTION_VIEW);
        String uri = "whatsapp://send?phone=" + urlId + "&text=" + msg;
        intent.setData(Uri.parse(uri));
        getActivity().startActivityForResult(intent, 745);
    }

    private void inits(View v) {
        presenter = new StepOrderPresenter(this);
        orderUPresenter = new OrderUPresenter(this);
        orderStepView = v.findViewById(R.id.orderStepView);
        nextOrderUButton = v.findViewById(R.id.nextOrderUButton);
        cancelOrderUButton = v.findViewById(R.id.cancelOrderUButton);
        contactImageButton = v.findViewById(R.id.contactImageButton);
        commentImageButton = v.findViewById(R.id.commentImageButton);
        nameTextView = v.findViewById(R.id.nameTextView);
        descriptionStepTextView = v.findViewById(R.id.descriptionStepTextView);
        topMenuFrameLayout = v.findViewById(R.id.topMenuFrameLayout);
        bottomMenuFrameLayout = v.findViewById(R.id.bottomMenuFrameLayout);

        if (typeView) {
            //topMenuFrameLayout.setVisibility(View.GONE);
            nameTextView.setText(MessageFormat.format("#{0}", orderModel.getOrderId()));
            commentImageButton.setVisibility(View.GONE);
            bottomMenuFrameLayout.setVisibility(View.GONE);
        } else{
            if(state == CANCELED){
                nextOrderUButton.setEnabled(false);
                cancelOrderUButton.setEnabled(false);
                contactImageButton.setEnabled(false);
            }
            nameTextView.setText(MessageFormat.format("Pedido #{0} de: {1}", orderModel.getOrderId(), orderModel.getCompanyName()));
        }


        orderStepView.setSteps(Arrays.asList(getString(
                R.string.pending), getString(R.string.acepted), getString(R.string.proccess), getString(R.string.finished)));
        orderStepView.go(orderModel.getState(), true);

        setTextState(state);


        loading = new LoadingDialog(getContext());
    }

    @Override
    public void OnSuccessNextStep(String message) {
        view.StepChanged(state, orderModel.getOrderId());
        if (state < 3) orderStepView.go(state, true);
        else {
            orderStepView.go(3, true);
            step = true;
        }
        setTextState(state);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnErrorNextStep(String message) {
        state--;
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(String message) {
        loading.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        loading.closeLoadingDialog();
    }

    @Override
    public void OnSuccessCancel(OrderModel orderModel, int position) {
        dismiss();
        view.OnOrderCanceled(orderModel, position);
    }

    @Override
    public void OnErrorCancel() {
        Toast.makeText(getContext(), "Hubo un error, no se pudo cancelar.", Toast.LENGTH_SHORT).show();
    }
}
