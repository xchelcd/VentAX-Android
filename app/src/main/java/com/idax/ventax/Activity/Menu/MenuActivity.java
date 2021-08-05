package com.idax.ventax.Activity.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.idax.ventax.Activity.Login.LoginActivity;
import com.idax.ventax.Drawer.DrawerAdapter;
import com.idax.ventax.Drawer.DrawerItem;
import com.idax.ventax.Drawer.SimpleItem;
import com.idax.ventax.Drawer.SpaceItem;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.FAQ;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.LoginModel;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Entity.Schedule;
import com.idax.ventax.Entity.SocialMedia;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.Fragment.Company.CompanyFragment;
import com.idax.ventax.Fragment.FAQ.FAQFragment;
import com.idax.ventax.Fragment.IDAX.IDAX;
import com.idax.ventax.Fragment.Menu.MenuFragment;
import com.idax.ventax.Fragment.Order.OrderCFragment;
import com.idax.ventax.Fragment.Product.ProductFragment;
import com.idax.ventax.Fragment.Profile.ProfileFragment;
import com.idax.ventax.Fragment.Settings.SettingsFragment;
import com.idax.ventax.Fragment.ShoppingCart.CartFragment;
import com.idax.ventax.R;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.idax.ventax.DataPersistence.DataPersistence.userDataPersistence;
import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY_LIST;
import static com.idax.ventax.Extra.Constansts.BUNDLE_FAQ_LIST;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INT_FOR_FAQ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_MODEL_ENTREPRENEURS;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;
import static com.idax.ventax.Extra.Constansts.INTENT_MODEL_LOGIN;
import static com.idax.ventax.Extra.Constansts.POS_CART;
import static com.idax.ventax.Extra.Constansts.POS_IDAX;
import static com.idax.ventax.Extra.Constansts.POS_MENU;
import static com.idax.ventax.Extra.Constansts.POS_ORDER;
import static com.idax.ventax.Extra.Constansts.POS_PROFILE;
import static com.idax.ventax.Extra.Constansts.POS_COMPANY;
import static com.idax.ventax.Extra.Constansts.POS_PRODUCT;
import static com.idax.ventax.Extra.Constansts.POS_SETTINGS;
import static com.idax.ventax.Extra.Constansts.POS_FAQ;
import static com.idax.ventax.Extra.Constansts.POS_LOGOUT;
import static com.idax.ventax.Extra.Constansts.RESULT_CODE_GO_TO_SHOPPING_CART;

@SuppressLint("ParcelCreator")
public class MenuActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, MenuUpdateObjects {

    private User user;
    private List<Company> companyList;
    private EntrepreneurModel modelEntrepreneur;
    private LoginModel modelLogin;

    private MenuUOPresenter presenter;
    private MenuUpdateObjects updateObjects = this;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    private DrawerAdapter drawerAdapter;

    private LoadingDialog dialog;

    private RecyclerView recyclerView;

    private Intent intent;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_GO_TO_SHOPPING_CART) {
            getBackFromOrderDialog();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //User user = userDataPersistence.getUserData();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu);
        firstInits();
        initDrawer(savedInstanceState);
    }

    private void firstInits() {
        presenter = new MenuUOPresenter(this);
        modelLogin = (LoginModel) getIntent().getSerializableExtra(INTENT_MODEL_LOGIN);
        user = modelLogin.getUser();
        companyList = modelLogin.getCompanyList();
        modelEntrepreneur = (user.getAffiliate() == AFFILIATE) ? modelLogin.getEntrepreneurModel() : new EntrepreneurModel();
        dialog = new LoadingDialog(getApplicationContext());
        FirebaseCrashlytics.getInstance().setUserId(String.valueOf((user.getId())));
    }

    private void initDrawer(Bundle savedInstanceState) {
        View v = LayoutInflater.from(this).inflate(R.layout.drawer_menu, null, false);
        //ImageButton imageButton = LayoutInflater.from(this).inflate(R.layout.fragment_menu, null, false).findViewById(R.id.showNavDrawerButton);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                //.withToolbarMenuToggle()
                .withMenuView(v)
                //.withMenuView(imageButton)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .inject();
        getSlidingRootNav(slidingRootNav);
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        drawerAdapter = fillListAdapter(user.getAffiliate());

        drawerAdapter.setListener(this);

        recyclerView = v.findViewById(R.id.drawerRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drawerAdapter);
        drawerAdapter.setSelected(POS_MENU);
    }

    private DrawerAdapter fillListAdapter(int affiliate) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new DrawerAdapter(Arrays.asList(
                createItemFor(POS_MENU).setChecked(true).setVisible(true),
                createItemFor(POS_PROFILE).setVisible(true),
                createItemFor(POS_CART).setVisible(true),
                createItemFor(POS_COMPANY).setVisible(affiliate == AFFILIATE),
                createItemFor(POS_ORDER).setVisible(affiliate == AFFILIATE),
                createItemFor(POS_PRODUCT).setVisible(affiliate == AFFILIATE),
                createItemFor(POS_SETTINGS).setVisible(affiliate == AFFILIATE),
                createItemFor(POS_FAQ).setVisible(affiliate == AFFILIATE),
                //new SpaceItem((int) (height - (10 * 185))),//240
                new SpaceItem(100),
                createItemFor(POS_IDAX).setVisible(true),
                createItemFor(POS_LOGOUT).setVisible(true)
        ));
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(getResources().getColor(R.color.blueGrayColorVentax))
                .withTextTint(getResources().getColor(R.color.whiteColorVentax))
                .withSelectedIconTint(getResources().getColor(R.color.fucsiaColorVentax))
                .withSelectedTextTint(getResources().getColor(R.color.blackColorVentax));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        if (position == POS_PROFILE) {
            ProfileFragment fragment = new ProfileFragment();
            bundle.putSerializable(BUNDLE_USER, user);
            bundle.putSerializable(BUNDLE_COMPANY, modelEntrepreneur.getCompany());
            bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == POS_CART) {
            CartFragment fragment = new CartFragment();
            bundle.putSerializable(BUNDLE_USER, user);
            //bundle.putSerializable(BUNDLE_COMPANY, modelEntrepreneur.getCompany());
            bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == POS_COMPANY) {
            CompanyFragment fragment = new CompanyFragment();
            bundle.putSerializable(BUNDLE_USER, user);
            bundle.putSerializable(BUNDLE_COMPANY, modelEntrepreneur.getCompany());
            bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == POS_ORDER) {
            OrderCFragment fragment = new OrderCFragment();
            bundle.putSerializable(BUNDLE_COMPANY, modelEntrepreneur.getCompany());
            bundle.putSerializable(BUNDLE_USER, user);
            bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == POS_PRODUCT) {
            ProductFragment fragment = new ProductFragment();
            bundle.putSerializable(BUNDLE_MODEL_ENTREPRENEURS, modelEntrepreneur);
            bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == POS_SETTINGS) {
            SettingsFragment fragment = new SettingsFragment();
            bundle.putSerializable(BUNDLE_USER, user);
            bundle.putSerializable(BUNDLE_MODEL_ENTREPRENEURS, modelEntrepreneur);
            bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == POS_FAQ) {
            FAQFragment fragment = new FAQFragment();
            bundle.putSerializable(BUNDLE_FAQ_LIST, (Serializable) modelEntrepreneur.getFaqList());
            bundle.putSerializable(BUNDLE_USER, user);
            bundle.putInt(BUNDLE_INT_FOR_FAQ, AFFILIATE);
            bundle.putSerializable(BUNDLE_COMPANY, modelEntrepreneur.getCompany());
            bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == POS_MENU) {
            MenuFragment fragment = new MenuFragment();
            bundle.putSerializable(BUNDLE_COMPANY_LIST, (Serializable) companyList);
            bundle.putSerializable(BUNDLE_USER, user);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else if (position == (POS_IDAX + 1)) {
            IDAX fragment = new IDAX();
            bundle.putSerializable(BUNDLE_USER, user);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
        } else {//if (position == POS_LOGOUT) {
            presenter.LogOutAccount(user.getId(), FirebaseInstanceId.getInstance().getToken());
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getBackFromOrderDialog() {
        drawerAdapter.setSelected(POS_CART);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        CartFragment fragment = new CartFragment();
        bundle.putSerializable(BUNDLE_USER, user);
        bundle.putParcelable(BUNDLE_INTERFACE_OBJ, updateObjects);
        fragment.setArguments(bundle);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void UpdateProfile(User user) {
        this.user = user;
    }

    @Override
    public void UpdateCompany(Company company) {
        modelEntrepreneur.setCompany(company);
    }

    @Override
    public void UpdateCompanySettings(Design design, List<Schedule> scheduleList, List<SocialMedia> socialMediaList) {
        modelEntrepreneur.setDesign(design);
        modelEntrepreneur.setScheduleList(scheduleList);
        modelEntrepreneur.setSocialMediaList(socialMediaList);
    }

    @Override
    public void UpdateProducts(List<Product> productList) {
        modelEntrepreneur.setProductList(productList);
    }

    @Override
    public void UpdateFAQList(List<FAQ> faqList) {
        modelEntrepreneur.setFaqList(faqList);
    }

    @Override
    public void onSuccessLogOut() {
        userDataPersistence.clearShardPreferences();
        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
        this.finish();
    }

    @Override
    public void onErrorLogOut(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


}