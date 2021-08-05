package com.idax.ventax.Dialog.Product;

import com.idax.ventax.Entity.Product;
import com.idax.ventax.Extra.IConst;

public interface ProductView extends IConst {

    void OnAddSuccess(Product product);
    void OnAddError(String message);
    void OnEditSuccess(Product product);
    void OnEditError(String message);
    void OnDeleteSuccess(String message);
    void OnDeleteError(String message);
}
