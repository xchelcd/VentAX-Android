package com.idax.ventax.Activity.Entrepreneur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idax.ventax.Adapter.ProductAdapter;
import com.idax.ventax.Dialog.AboutCompanyDialog;
import com.idax.ventax.BottomSheet.DetailsProduct;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Entity.User;
import com.idax.ventax.R;

import java.util.List;
import java.util.stream.Collectors;

import static com.idax.ventax.Extra.Constansts.INTENT_MODEL_ENTREPRENEUR;
import static com.idax.ventax.Extra.Constansts.INTENT_USER;
import static com.idax.ventax.Extra.Constansts.NOT_AFFILIATE;

public class EntrepreneurActivity extends AppCompatActivity implements EntrepreneurView {

    private EntrepreneurModel modelEntrepreneur;
    private List<Product> productList;
    private Design design;
    private Company company;
    private User user;

    private ProductAdapter adapter;
    private RecyclerView recyclerView;

    private TextView companyNameTextView;
    private LinearLayout mainCompanyLinearLayout;
    private ImageButton backImageButton;
    private SearchView searchProductSearchView;
    private LinearLayout.LayoutParams params1;

    private Intent intent;

    private EntrepreneurPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_entrepreneur);
        init();
        listener();
        test();
    }

    private void test() {

    }

    private void listener() {
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        companyNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ScheduleDialog(
                //        EntrepreneurActivity.this,
                //        modelEntrepreneur.getScheduleList(),
                //        null,
                //        companyNameTextView,
                //        null,
                //        modelEntrepreneur.getDesign().getDialog_background(),
                //        POSITION_BOTTOM).createMenuInformation();
                new AboutCompanyDialog(modelEntrepreneur).show(getSupportFragmentManager(), "AboutCompanyDialog");
            }
        });

        searchProductSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(EntrepreneurActivity.this, "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setAdapter(productList.stream().filter(x -> x.getName().toLowerCase().contains(newText.toLowerCase())).collect(Collectors.toList()), design);
                return false;
            }
        });
        searchProductSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params1.weight = 1.0f;
                searchProductSearchView.setLayoutParams(params1);
                companyNameTextView.setVisibility(View.GONE);
            }
        });
        searchProductSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                params1.weight = 0.0f;
                searchProductSearchView.setLayoutParams(params1);
                companyNameTextView.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void init() {
        presenter = new EntrepreneurPresenter(this);
        modelEntrepreneur = (EntrepreneurModel) getIntent().getSerializableExtra(INTENT_MODEL_ENTREPRENEUR);
        productList = modelEntrepreneur.getProductList();
        design = modelEntrepreneur.getDesign();
        company = modelEntrepreneur.getCompany();
        user = (User) getIntent().getSerializableExtra(INTENT_USER);

        companyNameTextView = findViewById(R.id.companyNameTextView);
        recyclerView = findViewById(R.id.recyclerView);
        mainCompanyLinearLayout = findViewById(R.id.mainCompanyLinearLayout);
        backImageButton = findViewById(R.id.backImageButton);
        searchProductSearchView = findViewById(R.id.searchProductSearchView);

        EditText searchEditText = ((EditText) searchProductSearchView.findViewById(R.id.search_src_text));
        searchEditText.setHintTextColor(getResources().getColor(R.color.grayColorVentax));
        searchEditText.setTextColor(getResources().getColor(R.color.blackColorVentax));

        setData();
        setAdapter(productList, design);

        params1 = (LinearLayout.LayoutParams) searchProductSearchView.getLayoutParams();
    }

    private void setData() {
        mainCompanyLinearLayout.setBackgroundColor(Color.parseColor(design.getMain_background()));
        companyNameTextView.setText(new StringBuffer().append(getString(R.string.welcome_to)).append(" ").append(company.getName()));
    }

    private void setAdapter(final List<Product> productList, final Design design) {
        adapter = new ProductAdapter(productList, design, NOT_AFFILIATE, company.getId(), company, getSupportFragmentManager());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, design.getColumns());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsProduct dialog = new DetailsProduct(
                        company,
                        productList.get(
                                recyclerView.getChildAdapterPosition(view)),
                        design, user);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void showProgressBar(String message) {

    }

    @Override
    public void hideProgressBar() {

    }
}