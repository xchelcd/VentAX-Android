package com.idax.ventax.Fragment.Product;

import com.idax.ventax.Entity.Product;
import com.idax.ventax.Extra.IConst;

public interface ProductView extends IConst {

    void onSuccessInsert(Product product);
    void onSuccessUpdate(Product product, int position, int totalItems);
    void onSuccessDelete(String message);
    void onError(String message);
}
