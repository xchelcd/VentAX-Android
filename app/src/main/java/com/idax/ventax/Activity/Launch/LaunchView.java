package com.idax.ventax.Activity.Launch;

import com.idax.ventax.Entity.LoginModel;

public interface LaunchView {
    void onUserSaved(LoginModel modelLogin);
    void onError(String message);
}
