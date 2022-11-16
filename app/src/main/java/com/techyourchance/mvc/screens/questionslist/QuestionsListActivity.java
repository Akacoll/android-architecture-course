package com.techyourchance.mvc.screens.questionslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.common.Constants;
import com.techyourchance.mvc.networking.QuestionSchema;
import com.techyourchance.mvc.networking.QuestionsListResponseSchema;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.BaseActivity;
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends BaseActivity implements FetchLastActiveQuestionsUseCase.Listener {

    private FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;

    private QuestionsListViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFetchLastActiveQuestionsUseCase = getCompositionRoot().getFetchLastActiveQuestionsUseCase();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(null);

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchLastActiveQuestionsUseCase.registerListener(this);

        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestionsAndNotify();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
    }

    private void bindQuestions(List<Question> questions) {
        mViewMvc.hideProgressIndication();
        for (Question question : questions) {
            questions.add(new Question(question.getId(), question.getTitle()));
        }
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        mViewMvc.hideProgressIndication();
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }
}
