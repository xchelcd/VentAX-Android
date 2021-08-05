package com.idax.ventax.Dialog.Enroll;

import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.IConst;

public interface EnrollView extends IConst {

    void onAuthSuccess(String message);
    void onInsertDataError(User user);
}
