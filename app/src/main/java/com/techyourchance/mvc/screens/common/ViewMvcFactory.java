package com.techyourchance.mvc.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.techyourchance.mvc.screens.questionslist.QuestionsListItemViewMvc;
import com.techyourchance.mvc.screens.questionslist.QuestionsListItemViewMvcImpl;
import com.techyourchance.mvc.screens.questionslist.QuestionsListViewMvc;
import com.techyourchance.mvc.screens.questionslist.QuestionsListViewMvcImpl;
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsViewMvc;
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsViewMvcImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public QuestionsListViewMvc getQuestionsListViewMvc(@Nullable ViewGroup parent) {
        return new QuestionsListViewMvcImpl(mLayoutInflater, parent, this);
    }

    public QuestionsListItemViewMvc getQuestionsListItemViewMvc(@Nullable ViewGroup parent) {
        return new QuestionsListItemViewMvcImpl(mLayoutInflater, parent);
    }

    public QuestionDetailsViewMvc getQuestionDetailsViewMvc(@Nullable ViewGroup parent) {
        return new QuestionDetailsViewMvcImpl(mLayoutInflater, parent);
    }
}
