package com.idax.ventax.Fragment.Product;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.idax.ventax.Activity.Menu.MenuUpdateObjects;
import com.idax.ventax.Adapter.ProductAdapter;
import com.idax.ventax.Dialog.InformationDialog;
import com.idax.ventax.Dialog.Product.ProductDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_MODEL_ENTREPRENEURS;
import static com.idax.ventax.Extra.Util.areDrawablesIdentical;
import static com.idax.ventax.Extra.Util.formatDecimal;
import static com.idax.ventax.Extra.Util.getNumOfItemsByAccountType;

public class ProductFragment extends Fragment implements ProductView {

    private MenuUpdateObjects updateObjects;
    private LoadingDialog dialog;
    private ProductPresenter presenter;

    private ProductView productView = this;

    //private ImageView oneProductImageView;
    //private ImageView twoProductImageView;
    //private ImageView threeProductImageView;
    //private EditText titleProductEditText;
    //private EditText priceProductEditText;
    //private EditText detailsProductEditText;
    //private EditText descriptionProductEditText;
    //private TextView idProductEditText;//CREAR NUEVO POSIBLE ID O PONER EL ID DEL ITEM SELECCIONADO
    //private Button okProductButton;
    //private Button editProductButton;
    //private LinearLayout backgroundDialogLinearLayout;
    //private LinearLayout mainBackgroundLinerLayout;
    private TextView accountTypeTextView;
    private TextView numberOfProductsTextView;
    private LinearLayout buttonsLinearLayout;

    private RecyclerView productsRecyclerView;
    private ProductAdapter adapter;

    private int index_ = -1;

    private TextView selectInfoTextView;

    public void setIndex(int index) {
        this.index_ = index;
    }

    private List<Product> productList;
    private Design design;
    private Company company;
    private EntrepreneurModel modelEntrepreneur;

    private int index;
    private Product product;
    private int totalItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modelEntrepreneur = (EntrepreneurModel) getArguments().get(BUNDLE_MODEL_ENTREPRENEURS);
            design = modelEntrepreneur.getDesign();
            company = modelEntrepreneur.getCompany();
            productList = modelEntrepreneur.getProductList();
            updateObjects = (MenuUpdateObjects) getArguments().get(BUNDLE_INTERFACE_OBJ);
            totalItems = (int) productList.stream().filter(x -> x.getState() == 1).count();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_product, container, false);
        inits(view);
        listerners();
        setData();
        setAdapter(productList);
        return view;
    }

    private void setAdapter(final List<Product> productList) {
        adapter = new ProductAdapter(productList, design, AFFILIATE, company.getId(), company, getParentFragmentManager(), productView);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), design.getColumns()));
        productsRecyclerView.setAdapter(adapter);
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        //itemTouchHelper.attachToRecyclerView(productsRecyclerView);
        adapter.setOnClickListener(view -> {
            //if (productList.size() < getNumOfItemsByAccountType(company.getPriority())) {
            try {
                index = productsRecyclerView.getChildAdapterPosition(view);
                product = productList.get(index);
                new ProductDialog(company.getId(), company.getName(), product, design, false, productView).show(getParentFragmentManager(), "ProductDialog");
            } catch (Exception e) {
                new ProductDialog(company.getId(), company.getName().toUpperCase(), new Product(), design, true, productView).show(getParentFragmentManager(), "ProductDialog");
            }
            //} else
            //  Toast.makeText(getContext(), "Alcanzó el límite de productos que permite su cuenta.", Toast.LENGTH_SHORT).show();

            //Snackbar.make(productsRecyclerView, productList.get(index).getName(), Snackbar.LENGTH_LONG)
            //        .setAction("Editar", new View.OnClickListener() {
            //            @Override
            //            public void onClick(View view) {
            //                setIndex(index);
            //                newButtonToCancelEdit();
            //                setDataInDialog(index);
            //                editProductButton.setEnabled(true);
            //                okProductButton.setEnabled(false);
            //            }
            //        }).show();
        });
    }

    private void setDataInDialog(int index) {
        //titleProductEditText.setText(productList.get(index).getName());
        //priceProductEditText.setText(formatDecimal(productList.get(index).getPrice() / 100));
        //idProductEditText.setText(MessageFormat.format("ID: {0}", productList.get(index).getId()));
        //descriptionProductEditText.setText(productList.get(index).getDescription());
        //detailsProductEditText.setText(productList.get(index).getDetails());
    }


    private void listerners() {
        accountTypeTextView.setOnClickListener(v -> {
            getSlidingRootNav(null).openMenu(true);
        });
        //okProductButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        if (checkImages() && checkText() && productList.size() <= getNumOfItemsByAccountType(company.getPriority())) {
        //            addProduct();//CHECAR CAMPOS CORRECTOS
        //            cleanFields();
        //            Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
        //        }
        //    }
        //});
        //editProductButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        if (checkText()) {
        //            editProduct();
        //            resetDialog();
        //            cleanFields();
        //            Toast.makeText(getContext(), "Editado", Toast.LENGTH_SHORT).show();
        //        }
        //    }
        //});
        selectInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Puedes ordenar los elementos de la lista de abajo para que aparezcan en el orden que gustes.
                //Manten presionado y arrastra al lugar que gustes.
                new InformationDialog(getContext(),
                        "Estos productos activos aparecerán cuando se entre a tu espacio en la app.\nEl número máximo de productos que se visualizarán dependerá sólo de los que estén activos.",
                        null, selectInfoTextView, null).createMenuInformation();
            }
        });
    }

    int numImg = 0;

    //private void editProduct() {
    //    Product p = new Product(productList.get(index_).getId(), titleProductEditText.getText().toString(),
    //            Double.parseDouble(priceProductEditText.getText().toString()
    //                    .replace("$", "")
    //                    .replace(",", "")
    //                    .replace(".", "")),
    //            descriptionProductEditText.getText().toString(),
    //            detailsProductEditText.getText().toString(), numImg, index_);
    //    presenter.updateProduct(p, company.getId());
    //}

    //private void addProduct() {
    //    Product p = new Product(0, titleProductEditText.getText().toString(),
    //            Double.parseDouble(priceProductEditText.getText().toString()
    //                    .replace("$", "")
    //                    .replace(",", "")
    //                    .replace(".", "")),
    //            descriptionProductEditText.getText().toString(),
    //            detailsProductEditText.getText().toString(), numImg, productList.size());
    //    presenter.insertProduct(p, company.getId());
    //}


    //private boolean checkImages() {
    //    Drawable drawable = getResources().getDrawable(R.drawable.idax);
    //    return (areDrawablesIdentical(drawable, oneProductImageView.getForeground())
    //            || areDrawablesIdentical(drawable, twoProductImageView.getForeground())
    //            || areDrawablesIdentical(drawable, threeProductImageView.getForeground()));
    //}

    //private boolean checkText() {
    //    return !(titleProductEditText.getText().toString().equals("") && priceProductEditText.getText().toString().equals(""));
    //}

    private void setData() {
        try {
            //titleProductEditText.setTextColor(Color.parseColor(design.getTitle_color()));
            accountTypeTextView.setTextColor(Color.parseColor(design.getTitle_color()));
            numberOfProductsTextView.setTextColor(Color.parseColor(design.getTitle_color()));
            //priceProductEditText.setTextColor(Color.parseColor(design.getTitle_color()));
            //descriptionProductEditText.setTextColor(Color.parseColor(design.getText_color()));
            //detailsProductEditText.setTextColor(Color.parseColor(design.getText_color()));
            //backgroundDialogLinearLayout.setBackgroundColor(Color.parseColor(design.getDialog_background()));
            //mainBackgroundLinerLayout.setBackgroundColor(Color.parseColor(design.getMain_background()));

            //numberOfProductsTextView.setText(MessageFormat.format("{0}/{1}",
            //        productList.size(), getNumOfItemsByAccountType(company.getPriority())));
            numberOfProductsTextView.setText(MessageFormat.format("{0}/{1}",
                    (int) productList.stream().filter(x -> x.getState() == 1).count(), getNumOfItemsByAccountType(company.getPriority())));
            //accountTypeTextView.setText(MessageFormat.format("{0} ({1})",
            //        company.getName(), Util.getAccountType(getContext(),
            //                company.getPriority())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private void cleanFields() {
    //    oneProductImageView.setForeground(getContext().getDrawable(R.drawable.idax));
    //    twoProductImageView.setForeground(getContext().getDrawable(R.drawable.idax));
    //    threeProductImageView.setForeground(getContext().getDrawable(R.drawable.idax));//80ECE007
    //    oneProductImageView.setBackgroundColor(Color.parseColor("#80ECE007"));
    //    twoProductImageView.setBackgroundColor(Color.parseColor("#80ECE007"));
    //    threeProductImageView.setBackgroundColor(Color.parseColor("#80ECE007"));
    //    titleProductEditText.setText("");
    //    priceProductEditText.setText("");
    //    descriptionProductEditText.setText("");
    //    detailsProductEditText.setText("");
    //}

    private void inits(View v) {
        presenter = new ProductPresenter(this);

        //oneProductImageView = v.findViewById(R.id.oneProductImageView);
        //twoProductImageView = v.findViewById(R.id.twoProductImageView);
        //threeProductImageView = v.findViewById(R.id.threeProductImageView);
        //titleProductEditText = v.findViewById(R.id.titleProductEditText);
        //priceProductEditText = v.findViewById(R.id.priceProductEditText);
        //detailsProductEditText = v.findViewById(R.id.detailsProductEditText);
        //descriptionProductEditText = v.findViewById(R.id.descriptionProductEditText);
        //idProductEditText = v.findViewById(R.id.idProductTextView);
        //okProductButton = v.findViewById(R.id.okProductButton);
        productsRecyclerView = v.findViewById(R.id.productsRecyclerView);
        //backgroundDialogLinearLayout = v.findViewById(R.id.backgroundDialogLinearLayout);
        //mainBackgroundLinerLayout = v.findViewById(R.id.mainBackgroundLinerLayout);
        accountTypeTextView = v.findViewById(R.id.accountTypeTextView);
        numberOfProductsTextView = v.findViewById(R.id.numberOfProductsTextView);
        //editProductButton = v.findViewById(R.id.editProductButton);
        buttonsLinearLayout = v.findViewById(R.id.buttonsLinearLayout);

        selectInfoTextView = v.findViewById(R.id.selectInfoTextView);

        dialog = new LoadingDialog(getContext());
    }


    private Product pAux;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END | ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int start = viewHolder.getAdapterPosition();
            int end = target.getAdapterPosition();
            Collections.swap(productList, start, end);
            //productsRecyclerView.getAdapter().notifyItemMoved(start, end);
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int index = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    if (index_ == index) {
                        Toast.makeText(getContext(),
                                "No se puede eliminar un producto en edición",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pAux = productList.get(index);
                    //productList.remove(index);
                    //adapter.notifyItemRemoved(index);
                    numberOfProductsTextView.setText(String.valueOf(productList.size()) + "/20");
                    Snackbar.make(productsRecyclerView, productList.get(index).getName(), Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    productList.add(index, pAux);
                                    adapter.notifyItemInserted(viewHolder.getAdapterPosition());
                                    numberOfProductsTextView.setText(String.valueOf(productList.size()) + "/20");
                                }
                            }).show();
                    break;
                case ItemTouchHelper.RIGHT:

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.parseColor("#DF2525"))
                    .addActionIcon(R.drawable.ic_baseline_delete_sweep_24)
                    .addSwipeLeftLabel("Eliminar")
                    .setSwipeLeftLabelColor(Color.parseColor("#000000"))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    //private void newButtonToCancelEdit() {
    //    if (buttonsLinearLayout.getChildAt(2) == null)
    //        buttonsLinearLayout.addView(newButton());
    //}

    //private Button newButton() {
    //    Button b = new Button(getContext());
    //    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    //    params.setMargins(25, 0, 0, 0);
    //    b.setLayoutParams(params);
    //    b.setText("Cancelar");
    //    b.setBackground(getResources().getDrawable(R.drawable.style_popup));
    //    b.setTextColor(Color.parseColor("#000000"));
    //    b.setGravity(Gravity.CENTER);
    //    b.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View view) {
    //            //cleanFields();
    //            resetDialog();
    //            index_ = -1;
    //        }
    //    });
    //    return b;
    //}

    //private void resetDialog() {
    //    buttonsLinearLayout.removeViewAt(2);
    //    okProductButton.setEnabled(true);
    //    editProductButton.setEnabled(false);
    //}

    @Override
    public void onSuccessInsert(Product product) {
        Toast.makeText(getContext(), "Agregado.", Toast.LENGTH_SHORT).show();
        productList.add(product);
        setAdapter(productList);
        numberOfProductsTextView.setText(MessageFormat.format("{0}/{1}",
                (int) productList.stream().filter(x -> x.getState() == 1).count(), getNumOfItemsByAccountType(company.getPriority())));
        updateObjects.UpdateProducts(productList);
    }

    @Override
    public void onSuccessUpdate(Product product, int position, int totalItems) {
        if (position == -1) {
            productList.set(index, product);
            adapter.notifyItemChanged(index);
        } else {
            numberOfProductsTextView.setText(MessageFormat.format("{0}/{1}",
                    totalItems, getNumOfItemsByAccountType(company.getPriority())));
            productList.set(position, product);
            adapter.notifyItemChanged(position);
        }
        Toast.makeText(getContext(), "Actualizado.", Toast.LENGTH_SHORT).show();
        updateObjects.UpdateProducts(productList);
    }

    @Override
    public void onSuccessDelete(String message) {
        Toast.makeText(getContext(), "Eliminado.", Toast.LENGTH_SHORT).show();
        productList.remove(product);
        adapter.notifyItemRemoved(index);
        totalItems = (int) productList.stream().filter(x -> x.getState() == 1).count();
        numberOfProductsTextView.setText(MessageFormat.format("{0}/{1}",
                totalItems, getNumOfItemsByAccountType(company.getPriority())));
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), "Ocurrió un error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(String message) {
        dialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        dialog.closeLoadingDialog();
    }
}
