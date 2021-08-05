package com.idax.ventax.Fragment.Order;

import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Extra.IConst;

import java.util.List;

public interface OrderCView extends IConst {
    void onSuccessResponse(List<OrderModel> orderModelList);
    void onErrorResponse(String message);
    void StepChanged(int state, int orderId);
    void OnOrderCanceled(OrderModel orderModel, int position);
}
