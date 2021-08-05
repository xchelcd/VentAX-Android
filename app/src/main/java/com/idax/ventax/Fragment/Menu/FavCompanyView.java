package com.idax.ventax.Fragment.Menu;

import com.idax.ventax.Entity.CompanySQLite;

public interface FavCompanyView {

    void OnInsertCompany(CompanySQLite companySQLite);
    void OnDeleteFavCompany(CompanySQLite companySQLite);
    void OnDeleteFavFromFavs(CompanySQLite companySQLite, int position);
}
