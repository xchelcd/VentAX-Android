package com.idax.ventax.Fragment.Company;

import com.idax.ventax.Extra.IConst;

public interface CompanyFView extends IConst {

    void OnUpdatePhotoSuccess();
    void OnError();

    void OnDesactivateSuccess(int state, String message);
    void onDesactiveError(String message);

    void onDeleteCompanySuccess(String message);
    void onDeleteCompanyError(String message);
}
