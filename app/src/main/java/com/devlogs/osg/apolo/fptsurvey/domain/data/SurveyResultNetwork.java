package com.devlogs.osg.apolo.fptsurvey.domain.data;


import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SurveyResultNetwork {
    Single<String> publishResult (String surveyId, List<Integer> answer, String idToken);
}
