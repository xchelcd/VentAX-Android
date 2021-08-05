package com.idax.ventax.BottomSheet.Order;

import com.idax.ventax.Extra.IConst;

public interface StepOrderView extends IConst {
    void OnSuccessNextStep(String message);
    void OnErrorNextStep(String message);
}
