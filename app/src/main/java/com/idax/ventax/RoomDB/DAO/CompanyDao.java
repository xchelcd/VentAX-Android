package com.idax.ventax.RoomDB.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.idax.ventax.Entity.CompanySQLite;

import java.util.List;

@Dao
public interface CompanyDao {

    @Query("SELECT * FROM companies")
    List<CompanySQLite> getAllFavCompanies();

    @Insert
    void insertFavCompany(CompanySQLite companySQLite);

    @Delete
    void deleteFavCompany(CompanySQLite companySQLite);

    @Query("SELECT * FROM companies WHERE id=:companyId")
    CompanySQLite getCompanyById(int companyId);

    @Query("SELECT EXISTS(SELECT * FROM companies WHERE id=:companyId)")
    boolean hasExist(int companyId);
}
