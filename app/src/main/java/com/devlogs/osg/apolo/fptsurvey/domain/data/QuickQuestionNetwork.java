package com.devlogs.osg.apolo.fptsurvey.domain.data;

import com.devlogs.osg.apolo.fptsurvey.domain.data.model.QuickQuestionNetworkModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface QuickQuestionNetwork {

    Single<List<QuickQuestionNetworkModel>> getAllQuickQuestion ();
}
