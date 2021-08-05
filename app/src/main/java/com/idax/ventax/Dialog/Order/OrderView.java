package com.idax.ventax.Dialog.Order;

import com.idax.ventax.Extra.IConst;

public interface OrderView extends IConst {
    void onSuccessOrderNotification(String message);
    void onErrorOrderNotification(String message);
}
