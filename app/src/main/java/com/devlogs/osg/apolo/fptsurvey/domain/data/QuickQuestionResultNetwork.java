package com.devlogs.osg.apolo.fptsurvey.domain.data;

import io.reactivex.rxjava3.core.Completable;

public interface QuickQuestionResultNetwork {
     Completable pushResult (String questionId, int answer, String email);
}
