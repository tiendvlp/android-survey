package com.devlogs.osg.apolo.fptsurvey.surveyquestion;

import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyQuestionNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyQuestionNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.SurveyQuestionEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAllQuestionInSurveyUseCaseSync {
    private SurveyQuestionNetwork mSurveyQuestionNetwork;
    private AuthDisk mAuth;

    @Inject
    public GetAllQuestionInSurveyUseCaseSync(SurveyQuestionNetwork mSurveyQuestionNetwork, AuthDisk authDisk) {
        this.mSurveyQuestionNetwork = mSurveyQuestionNetwork;
        this.mAuth = authDisk;
    }

    public Single<List<SurveyQuestionEntity>> execute (String surveyId) {

        return Single.create(new SingleOnSubscribe<List<SurveyQuestionEntity>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<SurveyQuestionEntity>> emitter) throws Throwable {
                mAuth.getAccessToken().subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        mSurveyQuestionNetwork.getSurveyQuestion(s, surveyId)
                                .subscribe(new Consumer<List<SurveyQuestionNetworkModel>>() {
                                    @Override
                                    public void accept(List<SurveyQuestionNetworkModel> questions) throws Throwable {
                                        List<SurveyQuestionEntity> result = new ArrayList();

                                        for (SurveyQuestionNetworkModel question : questions) {
                                            result.add(new SurveyQuestionEntity(question.getId(), question.getQuestion(), question.getAnswers()));
                                        }

                                        emitter.onSuccess(result);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Throwable {
                                        emitter.onError(throwable);
                                    }
                                });
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
