package com.techyourchance.mvc.questions;

import com.techyourchance.mvc.common.BaseObservable;
import com.techyourchance.mvc.networking.QuestionDetailsResponseSchema;
import com.techyourchance.mvc.networking.QuestionSchema;
import com.techyourchance.mvc.networking.StackoverflowApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    private final StackoverflowApi mStackoverflowApi;

    public interface Listener {
        void onQuestionDetailsFetched(QuestionDetails questionDetails);
        void onQuestionDetailsFetchFailed();
    }

    public FetchQuestionDetailsUseCase(StackoverflowApi stackoverflowApi) {
        mStackoverflowApi = stackoverflowApi;
    }

    public void fetchQuestionsDetailsAndNotify(String questionId) {
        mStackoverflowApi.fetchQuestionDetails(questionId)
                .enqueue(new Callback<QuestionDetailsResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionDetailsResponseSchema> call, Response<QuestionDetailsResponseSchema> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            notifySuccess(response.body().getQuestion());
                        } else {
                            notifyFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable t) {
                        notifyFailure();
                    }
                });
    }

    private void notifySuccess(QuestionSchema questionSchema) {
        for (Listener listener : getListeners()) {
            listener.onQuestionDetailsFetched(
                    new QuestionDetails(
                            questionSchema.getId(),
                            questionSchema.getTitle(),
                            questionSchema.getBody()
                    ));
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onQuestionDetailsFetchFailed();
        }
    }
}
