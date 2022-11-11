package com.techyourchance.mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.networking.QuestionDetailsResponseSchema;
import com.techyourchance.mvc.networking.QuestionSchema;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.BaseActivity;
import com.techyourchance.mvc.screens.questionslist.QuestionsListItemViewMvc;
import com.techyourchance.mvc.screens.questionslist.QuestionsListItemViewMvcImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailsActivity extends BaseActivity implements QuestionsListItemViewMvc.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    private StackoverflowApi mStackoverflowApi;

    private QuestionDetailsViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStackoverflowApi = getCompositionRoot().getStackoverflowApi();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(null);

        setContentView(mViewMvc.getRootView());
    }

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.showProgressIndication();
        String questionId = getIntent().getStringExtra(EXTRA_QUESTION_ID);
        fetchQuestionDetails(questionId);
    }

    private void fetchQuestionDetails(String questionId) {
        mStackoverflowApi.fetchQuestionDetails(questionId)
                .enqueue(new Callback<QuestionDetailsResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionDetailsResponseSchema> call, Response<QuestionDetailsResponseSchema> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            bindQuestionDetails(response.body().getQuestion());
                        } else {
                            networkCallFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable t) {
                        networkCallFailed();
                    }
                });
    }

    private void bindQuestionDetails(QuestionSchema questionSchema) {
        mViewMvc.hideProgressIndication();
        QuestionDetails questionDetails = new QuestionDetails(
                questionSchema.getId(),
                questionSchema.getTitle(),
                questionSchema.getBody()
        );
        mViewMvc.bindQuestion(questionDetails);
    }

    private void networkCallFailed() {
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQuestionClicked(Question question) {

    }
}
