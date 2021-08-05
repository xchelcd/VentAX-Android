package com.idax.ventax.Fragment.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.Adapter.CompanyAdapter;
import com.idax.ventax.Adapter.FavAdapter;
import com.idax.ventax.BottomSheet.CompanyBottomSheet;
import com.idax.ventax.Dialog.Filter.ChipFilterView;
import com.idax.ventax.Dialog.FilterBusinessDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.CompanySQLite;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;
import com.idax.ventax.RoomDB.AppDataBase.AppDataBase;
import com.idax.ventax.RoomDB.DAO.CompanyDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY_LIST;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;

public class MenuFragment extends Fragment implements MenuView, ChipFilterView, FavCompanyView {

    private ImageButton showNavDrawerButton;

    private List<Company> companyList;
    private List<Company> list;
    private User user;

    private FavAdapter favAdapter;
    private CompanyAdapter adapter;
    private RecyclerView favRecyclerView;
    private RecyclerView recyclerView;
    private SearchView searchCompanySearchView;

    private LoadingDialog dialog;

    private MenuPresenter presenter;

    private ImageButton filterByImageButton;

    private ChipFilterView chipFilterView = this;

    private List<Integer> integerList;

    private AppDataBase appDataBase;
    private CompanyDao companyDao;
    private List<CompanySQLite> companySQLiteList = new ArrayList<>();

    private FavCompanyView favCompanyView = this;

    private TextView withOutFavTextView;

    //TODO : CREAR UNA LSITA QUE VA A SESR FIJA Y UNA LISTA DINÁMICA

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyList = (List<Company>) getArguments().get(BUNDLE_COMPANY_LIST);
            user = (User) getArguments().get(BUNDLE_USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);
        inits(v);
        setAdapter(companyList);
        setFavAdapter(companySQLiteList);
        listener();
        return v;
    }

    private EditText searchEditText;
    private ImageView seachIamgeView;

    private void inits(ViewGroup v) {
        integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        dialog = new LoadingDialog(getContext());
        presenter = new MenuPresenter(this, getContext());
        recyclerView = v.findViewById(R.id.recyclerView);
        favRecyclerView = v.findViewById(R.id.favRecyclerView);
        searchCompanySearchView = v.findViewById(R.id.searchCompanySearchView);
        filterByImageButton = v.findViewById(R.id.filterByImageButton);
        //searchCompanySearchView.setTextColor(getResources().getColor(R.color.blackColorVentax));
        searchEditText = ((EditText) searchCompanySearchView.findViewById(R.id.search_src_text));
        searchEditText.setHintTextColor(getResources().getColor(R.color.grayColorVentax));
        searchEditText.setTextColor(getResources().getColor(R.color.blackColorVentax));
        showNavDrawerButton = v.findViewById(R.id.showNavDrawerButton);
        withOutFavTextView = v.findViewById(R.id.withOutFavTextView);

        v.findViewById(R.id.noRegisterTextView).setVisibility(companyList.size() == 0 ? View.VISIBLE : View.GONE);

        appDataBase = AppDataBase.getAppDataBase(getContext());
        companyDao = appDataBase.companyDao();
        companySQLiteList = companyDao.getAllFavCompanies();
        withOutFavTextView.setVisibility((companySQLiteList.size() == 0) ? View.VISIBLE : View.GONE);
    }

    private void listener() {
        filterByImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FilterBusinessDialog(chipFilterView, integerList).show(getParentFragmentManager(), "FilterBusinessDialog");
            }
        });
        searchCompanySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //setAdapter(companyList.stream().filter(x -> x.getName().toLowerCase().contains(newText.toLowerCase())).collect(Collectors.toList()));
                setAdapter(companyList.stream()
                        .filter(x -> x.getName().toLowerCase().contains(newText.toLowerCase()))
                        .filter(business -> new HashSet<>(integerList)
                                .contains(business.getCategory()))
                        .collect(Collectors.toList()));
                return false;
            }
        });
        showNavDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSlidingRootNav(null).openMenu(true);
            }
        });
    }

    private void setAdapter(final List<Company> companyList) {
        adapter = new CompanyAdapter(companyList, favCompanyView, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo checar si es mejor cambiar a POST en vez de GET
                presenter.GetCompanyEntrepreneurModel(companyList.get(recyclerView.getChildAdapterPosition(v)).getId());
                Toast.makeText(getContext(), companyList.get(recyclerView.getChildAdapterPosition(v)).getName(), Toast.LENGTH_SHORT).show();
                //new CompanyBottomSheet(companyList.get(recyclerView.getChildAdapterPosition(v))).show(getParentFragmentManager(),"CompanyBottomSheet");
            }
        });
    }

    private void setFavAdapter(List<CompanySQLite> companySQLiteList) {
        favAdapter = new FavAdapter(companySQLiteList, favCompanyView, getContext());
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        favRecyclerView.setAdapter(favAdapter);
        favAdapter.setOnClickListener(v -> {
            presenter.GetCompanyEntrepreneurModel(companySQLiteList.get(favRecyclerView.getChildAdapterPosition(v)).getId());
            Toast.makeText(getContext(), companySQLiteList.get(favRecyclerView.getChildAdapterPosition(v)).getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onSuccessCompanyEntrepreneurModel(EntrepreneurModel modelEntrepreneur) {
        new CompanyBottomSheet(modelEntrepreneur, user).show(getParentFragmentManager(), "CompanyBottomSheet");
    }

    @Override
    public void onErrorCompanyEntrepreneurMode(String message) {

    }

    @Override
    public void showProgressBar(String message) {
        dialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        dialog.closeLoadingDialog();
    }

    @Override
    public void getAllCategoriesId(List<Integer> categoriesIdList) {
        searchCompanySearchView.setQuery("", false);
        integerList = categoriesIdList;
        setAdapter(companyList.stream()
                .filter(c -> categoriesIdList.stream()
                        .filter(x -> x >= 0)
                        .collect(Collectors.toList())
                        .contains(c.getCategory()))
                .collect(Collectors.toList()));


    }

    //todo checar si se necesita la variable "position"
    @Override
    public void OnInsertCompany(CompanySQLite companySQLite) {
        companySQLiteList.add(companySQLite);
        favAdapter.notifyDataSetChanged();
        //todo agregar al adapter
        //adapter.notify();
        //adapter.notifyAll();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void OnDeleteFavCompany(CompanySQLite companySQLite) {
        //CompanySQLite obj = companySQLiteList.stream().findAny().filter(x -> x.getId() == companySQLite.getId()).orElse(null);
        CompanySQLite obj = null;
        for (CompanySQLite c : companySQLiteList) {
            if ( c.getId() == companySQLite.getId()){
                obj = c;
                break;
            }
        }
        int index = companySQLiteList.indexOf(obj);
        if (index != -1) {
            deleteFromFav(index);
        } else Toast.makeText(getContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show();
        //todo eliminar del adapter
    }

    @Override
    public void OnDeleteFavFromFavs(CompanySQLite companySQLite, int position) {
        deleteFromFav(position);
    }

    private void deleteFromFav(int index) {
        companySQLiteList.remove(index);
        favAdapter.notifyItemRemoved(index);
        withOutFavTextView.setVisibility((companySQLiteList.size() == 0) ? View.VISIBLE : View.GONE);
        setFavAdapter(companySQLiteList);
        setAdapter(companyList.stream()
                .filter(c -> integerList.stream()
                        .filter(x -> x >= 0)
                        .collect(Collectors.toList())
                        .contains(c.getCategory()))
                .collect(Collectors.toList()));
        Toast.makeText(getContext(), "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
    }
    /*
    setAdapter(companyList.stream()
                .filter(x -> x.getName().toLowerCase().contains(""))
                .filter(business -> new HashSet<>(integerList)
                        .contains(business.getCategory()))
                .collect(Collectors.toList()));
     */
    /*
    return businessAllModel.getBusinessList()
                .stream()
                .filter(business -> businessAllModel.getReservationList()
                        .stream()
                        .filter(x -> x.getAtProgress() == type)
                        //.collect(Collectors.toList())
                        //.stream()
                        .map(Reservation::getBusiness_id)
                        .collect(Collectors.toSet())
                        .contains(business.getId()))
                .collect(Collectors.toList());
     */
}
