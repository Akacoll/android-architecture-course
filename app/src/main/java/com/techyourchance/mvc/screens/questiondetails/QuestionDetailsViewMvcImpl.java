package com.techyourchance.mvc.screens.questiondetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.BaseViewMvc;

public class QuestionDetailsViewMvcImpl extends BaseViewMvc implements QuestionDetailsViewMvc {
    private final TextView mTxtTitle;
    private final TextView mTxtBody;
    private final ProgressBar mProgress;

    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_question_details, parent, false));

        mTxtTitle = findViewById(R.id.txt_question_title);
        mTxtBody = findViewById(R.id.txt_question_body);
        mProgress = findViewById(R.id.progress);

    }

    @Override
    public void showProgressIndication() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindQuestion(QuestionDetails question) {
        mTxtBody.setText(question.getBody());
        mTxtTitle.setText(question.getTitle());
    }

    @Override
    public void hideProgressIndication() {
        mProgress.setVisibility(View.GONE);
    }
}
