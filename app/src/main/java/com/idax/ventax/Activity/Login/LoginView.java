package com.idax.ventax.Activity.Login;

import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.LoginModel;
import com.idax.ventax.Extra.IConst;
import com.idax.ventax.Entity.User;

import java.util.List;

public interface LoginView extends IConst  {

    void onHasDataSaved(LoginModel modelLogin);

    void onSuccessCredentials(LoginModel modelLogin);
    void onErrorCredentials(String message);

    void onFinalErrorRequest(String message);

    void onFinalSuccessRequest(User user, List<Company> companyList);

    void onSuccessUserSaved(LoginModel modelLogin);
}
