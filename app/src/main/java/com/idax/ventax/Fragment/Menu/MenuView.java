package com.idax.ventax.Fragment.Menu;

import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Extra.IConst;

public interface MenuView extends IConst {

    void onSuccessCompanyEntrepreneurModel(EntrepreneurModel company);
    void onErrorCompanyEntrepreneurMode(String message);
}
