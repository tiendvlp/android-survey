package com.devlogs.osg.apolo.fptsurvey.quickquestion;

import com.devlogs.osg.apolo.fptsurvey.domain.data.QuickQuestionNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.QuickQuestionResultNetwork;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PushQuickQuestionResultUseCase {
    private QuickQuestionResultNetwork mQuickQuestionResultNetwork;

    @Inject
    public PushQuickQuestionResultUseCase (QuickQuestionResultNetwork quickQuestionResultNetwork) {
        mQuickQuestionResultNetwork = quickQuestionResultNetwork;
    }

    public Completable PushQuickQuestionResultUseCase (String questionId, int answer, String email) {
        return mQuickQuestionResultNetwork.pushResult(questionId, answer, email).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
