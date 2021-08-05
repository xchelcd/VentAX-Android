package com.idax.ventax.Activity.Order;

import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Extra.IConst;

public interface OrderUView extends IConst {

    void OnSuccessCancel(OrderModel orderModel, int position);
    void OnErrorCancel();
}
