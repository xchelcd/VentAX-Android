package com.idax.ventax.Fragment.Settings;

import com.idax.ventax.Entity.CompanyConfigurationModel;
import com.idax.ventax.Extra.IConst;

public interface SettingsFView extends IConst {

    void OnSuccessDataSaved(CompanyConfigurationModel companyConfigurationModel);
    void OnErrorDataSaved();
}
