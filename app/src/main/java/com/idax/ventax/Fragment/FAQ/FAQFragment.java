package com.idax.ventax.Fragment.FAQ;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.idax.ventax.Activity.Menu.MenuUpdateObjects;
import com.idax.ventax.Adapter.FAQAdapter;
import com.idax.ventax.Dialog.InformationDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.FAQ;
import com.idax.ventax.Entity.FAQModel;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.util.List;

import static com.idax.ventax.Drawer.SlidingRootNavigation.getSlidingRootNav;
import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Constansts.BUNDLE_COMPANY;
import static com.idax.ventax.Extra.Constansts.BUNDLE_FAQ_LIST;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INTERFACE_OBJ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_INT_FOR_FAQ;
import static com.idax.ventax.Extra.Constansts.BUNDLE_USER;

public class FAQFragment extends Fragment implements FAQView {

    private MenuUpdateObjects updateObjects;

    private User user;
    private List<FAQ> faqList;
    private Company company;
    private int affiliate;

    private ExpandableListView faqExpandableListView;

    private LinearLayout newFAQLinearLayout;

    //private EditText question;
    //private EditText answer;
    //private Button setFAQ;
    private LinearLayout.LayoutParams layoutParams;
    private LinearLayout linearLayout;
    private Button editFAQButton;
    private Button deleteFAQButton;
    private Button clearFieldsFAQsButton;
    private Button saveAllFAQsButton;
    private EditText questionEditText;
    private EditText answerEditText;

    private TextView informationFAQTextView;


    private int questionId = -1;

    private FAQAdapter adapter;

    private FAQPresenter presenter;

    private LoadingDialog loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            faqList = (List<FAQ>) getArguments().get(BUNDLE_FAQ_LIST);
            user = (User) getArguments().get(BUNDLE_USER);
            company = (Company) getArguments().get(BUNDLE_COMPANY);
            updateObjects = (MenuUpdateObjects) getArguments().get(BUNDLE_INTERFACE_OBJ);
            affiliate = getArguments().getInt(BUNDLE_INT_FOR_FAQ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_faq, container, false);
        inits(root);
        listeners();
        setAdapter(faqList);
        getReceiveData(affiliate);
        return root;
    }

    private void listeners() {
        if (affiliate == AFFILIATE) {
            saveAllFAQsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQModel faqModel = new FAQModel(company.getId(), faqList);
                    presenter.UpdateFAQs(faqModel);
                }
            });
            informationFAQTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (affiliate == AFFILIATE) {
                        getSlidingRootNav(null).openMenu(true);
                    }else{
                        getActivity().onBackPressed();
                    }
                    //new InformationDialog(getContext(),
                    //        getString(R.string.information_faq),
                    //        null, informationFAQTextView, null).createMenuInformation();
                }
            });
            editFAQButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String question = questionEditText.getText().toString();
                    String answer = answerEditText.getText().toString();
                    //editFAQButton.setText(getString(R.string.edit_question_n));
                    //deleteFAQButton.setText(getString(R.string.eliminar_q));
                    if (!question.equals("") && !answer.equals("")) {
                        if (questionId >= 0 && questionId < faqList.size())
                            faqList.set(questionId, new FAQ(question, answer));
                        else if (questionId >= faqList.size())
                            faqList.add(new FAQ(question, answer));
                        else
                            Toast.makeText(getContext(), getString(R.string.select_a_question), Toast.LENGTH_SHORT).show();
                        setAdapter(faqList);
                    }
                    //questionEditText.setText("");
                    //answerEditText.setText("");
                    //questionId = -1;
                    clearFields();
                }
            });
            deleteFAQButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        faqList.remove(questionId);
                        setAdapter(faqList);
                        Toast.makeText(getContext(), getString(R.string.question_deleted), Toast.LENGTH_SHORT).show();
                        clearFields();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Seleccione una pregunta", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            clearFieldsFAQsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    faqExpandableListView.collapseGroup(questionId);
                    clearFields();
                }
            });
        }
        else{
            informationFAQTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        getActivity().onBackPressed();
                    //new InformationDialog(getContext(),
                    //        getString(R.string.information_faq),
                    //        null, informationFAQTextView, null).createMenuInformation();
                }
            });
        }
    }

    private void clearFields() {
        questionEditText.setText("");
        answerEditText.setText("");
        questionId = -1;
        editFAQButton.setText(getString(R.string.edit_question_n));
        deleteFAQButton.setText(getString(R.string.eliminar_q));
    }

    private void getReceiveData(int affiliate) {
        if (affiliate == AFFILIATE) {
            newFAQLinearLayout.setVisibility(View.VISIBLE);
            saveAllFAQsButton.setVisibility(View.VISIBLE);
        }else{
            informationFAQTextView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_arrow_back_ios_24), null, null, null);
        }
    }


    private void inits(View v) {
        newFAQLinearLayout = v.findViewById(R.id.newFAQLinearLayout);
        faqExpandableListView = v.findViewById(R.id.faqExpandableListView);
        informationFAQTextView = v.findViewById(R.id.informationFAQTextView);

        editFAQButton = v.findViewById(R.id.editFAQButton);
        deleteFAQButton = v.findViewById(R.id.deleteFAQButton);
        clearFieldsFAQsButton = v.findViewById(R.id.clearFieldsFAQsButton);
        saveAllFAQsButton = v.findViewById(R.id.saveAllFAQsButton);
        questionEditText = v.findViewById(R.id.questionEditText);
        answerEditText = v.findViewById(R.id.answerEditText);

        presenter = new FAQPresenter(this);
        loading = new LoadingDialog(getContext());

        //presenter.UpdateFAQs(company.getId(), faqList);
    }

    private void setAdapter(final List<FAQ> faqList) {
        adapter = new FAQAdapter(getContext(), faqList, affiliate, company.getPriority());

        faqExpandableListView.setAdapter(adapter);
        faqExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                questionId = i;
                try {
                    editFAQButton.setText(new StringBuffer().append(getString(R.string.edit_question_n)).append(i + 1));
                    deleteFAQButton.setText(new StringBuffer().append(getString(R.string.eliminar_q)).append(i + 1));
                    if (i < faqList.size()) {
                        questionEditText.setText(faqList.get(i).getQuestion());
                        answerEditText.setText(faqList.get(i).getAnswer());
                    } else {
                        questionEditText.setText(new StringBuffer().append(getString(R.string.question)).append(" ").append(i + 1));
                        answerEditText.setText(new StringBuffer().append(getString(R.string.answer)).append(" ").append(i + 1));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    //private void createTextView() {
    //    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    //    newFAQLinearLayout.addView(linearLayout(layoutParams));
    //}

    //private EditText setQuestionsParams(LinearLayout.LayoutParams layoutParams) {
    //    layoutParams.setMargins(30, 500, 30, 30);
    //    question = new EditText(getContext());
    //    question.setPadding(20,20,20,20);
    //    question.setTextColor(getContext().getColor(R.color.blackColorVentax));
    //    question.setLayoutParams(layoutParams);
    //    question.setHint("Preguntas");
    //    question.setBackground(getContext().getDrawable(R.drawable.style_custom_selected_edittext));
//
    //    question.setHint(getString(R.string.question));
    //    return question;
    //}

    //@SuppressLint("UseCompatLoadingForDrawables")
    //private EditText setAnswerParams(LinearLayout.LayoutParams layoutParams) {
    //    layoutParams.setMargins(70, 5, 70, 0);
    //    answer = new EditText(getContext());
    //    answer.setPadding(20,20,20,20);
    //    answer.setTextColor(getContext().getColor(R.color.blackColorVentax));
    //    answer.setLayoutParams(layoutParams);
    //    answer.setHint("Respuestas");
    //    answer.setBackground(getContext().getDrawable(R.drawable.style_custom_selected_edittext));
//
    //    answer.setHint("R...");
    //    return answer;
    //}

    //@SuppressLint("UseCompatLoadingForDrawables")
    //private Button setFAQParams() {
    //    setFAQ = new Button(getContext());
    //    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    //    layoutParams.setMargins(5, 5, 5, 5);
    //    setFAQ.setLayoutParams(layoutParams);
    //    setFAQ.setGravity(Gravity.CENTER);
    //    setFAQ.setText(getString(R.string.edit_question_n));
    //    setFAQ.setTextColor(getContext().getColor(R.color.blackColorVentax));
    //    setFAQ.setBackground(getContext().getDrawable(R.drawable.style_popup));
    //    setFAQ.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View view) {
    //            setFAQ.setText(getString(R.string.edit_question_n));
    //            if (!question.getText().toString().equals("") && !answer.getText().toString().equals("")) {
    //                if (questionId >= 0 && questionId < faqList.size())
    //                    faqList.set(questionId, new FAQ(question.getText().toString(), answer.getText().toString()));
    //                else if (questionId >= faqList.size())
    //                    faqList.add(new FAQ(question.getText().toString(), answer.getText().toString()));
    //                else
    //                    Toast.makeText(getContext(), getString(R.string.select_a_question), Toast.LENGTH_SHORT).show();
    //                setAdapter(faqList);
    //            } else if (question.getText().toString().equals(new StringBuffer().append(getString(R.string.question)).append(" ").append(questionId)) ||
    //                    question.getText().toString().equals(new StringBuffer().append(getString(R.string.answer)).append(" ").append(questionId)) ||
    //                    question.getText().toString().isEmpty()) {
    //                //TODO: eliminar la pregunta si se deja en blanco
    //                try {
    //                    faqList.remove(questionId);
    //                    setAdapter(faqList);
    //                    Toast.makeText(getContext(), getString(R.string.question_deleted), Toast.LENGTH_SHORT).show();
    //                } catch (Exception e) {
    //                    Toast.makeText(getContext(), "Seleccione una pregunta", Toast.LENGTH_SHORT).show();
    //                }
    //            }
    //            question.setText("");
    //            answer.setText("");
    //            questionId = -1;
    //        }
    //    });
    //    return setFAQ;
    //}

    //private LinearLayout linearLayout(LinearLayout.LayoutParams layoutParams) {
    //    linearLayout = new LinearLayout(getContext());
    //    linearLayout.setLayoutParams(layoutParams);
    //    linearLayout.setGravity(Gravity.CENTER);
    //    linearLayout.setOrientation(LinearLayout.VERTICAL);
//
    //    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    //    linearLayout.addView(setQuestionsParams(layoutParams));
    //    linearLayout.addView(setAnswerParams(layoutParams));
    //    linearLayout.addView(setFAQParams());
    //    return linearLayout;
    //}

    @Override
    public void showProgressBar(String message) {
        loading.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        loading.closeLoadingDialog();
    }

    @Override
    public void OnSuccessFAQ() {
        updateObjects.UpdateFAQList(faqList);
    }

    @Override
    public void OnErrorFAQ(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
