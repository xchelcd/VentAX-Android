package com.idax.ventax.Fragment.IDAX;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.idax.ventax.Activity.Menu.MenuUpdateObjects;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;

public class IDAX extends Fragment implements IDAXView {

    private User user;

    private IDAXPresenter presenter;

    private LoadingDialog loading;

    private Button sendSuggestionButton;
    private EditText suggestionTextView;

    private TextView accountTypeTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().get(BUNDLE_USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_idax, container, false);
        inits(view);
        listeners();
        return view;
    }

    private void listeners() {
        sendSuggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suggestion = suggestionTextView.getText().toString();
                if (suggestionTextView.getText().toString().isEmpty()) return;
                if (suggestion.length() >= 2) presenter.InsertSuggestion(user.getId(), suggestion);
            }
        });
        accountTypeTextView.setOnClickListener(v->{
            getSlidingRootNav(null).openMenu(true);
        });

    }

    private void inits(ViewGroup v) {
        accountTypeTextView = v.findViewById(R.id.accountTypeTextView);
        sendSuggestionButton = v.findViewById(R.id.sendSuggestionButton);
        suggestionTextView = v.findViewById(R.id.suggestionTextView);

        presenter = new IDAXPresenter(this);
        loading = new LoadingDialog(getContext());
    }

    @Override
    public void OnSuccessSuggestion(String message) {
        suggestionTextView.setText("");
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnErrorSuggestion(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(String message) {
        loading.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        loading.closeLoadingDialog();
    }
}
