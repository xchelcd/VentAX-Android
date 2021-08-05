package com.idax.ventax.Fragment.IDAX;

import com.idax.ventax.Extra.IConst;

public interface IDAXView extends IConst {
    void OnSuccessSuggestion(String message);
    void OnErrorSuggestion(String message);
}
