package com.devlogs.osg.apolo.fptsurvey.surveytopic;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyTopicNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyTopicNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.SurveyTopicEntity;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAllSurveyTopicUseCaseSync {
    private SurveyTopicNetwork mSurveyTopicNetwork;
    private AuthDisk mAuth;

    @Inject
    public GetAllSurveyTopicUseCaseSync(SurveyTopicNetwork surveyTopicNetwork, AuthDisk authDisk) {
        mSurveyTopicNetwork = surveyTopicNetwork;
        mAuth = authDisk;
    }

    public Single<List<SurveyTopicEntity>> execute () {
        return Single.create(new SingleOnSubscribe<List<SurveyTopicEntity>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<SurveyTopicEntity>> emitter) throws Throwable {
                mAuth.getAccessToken().subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        mSurveyTopicNetwork.getAllSurveyTopic(s)
                                .subscribe(new Consumer<List<SurveyTopicNetworkModel>>() {
                                    @Override
                                    public void accept(List<SurveyTopicNetworkModel> surveyTopicNetworkModels) throws Throwable {
                                        ArrayList<SurveyTopicEntity> result = new ArrayList();
                                        for (SurveyTopicNetworkModel surveyTopicDao : surveyTopicNetworkModels) {
                                            result.add(new SurveyTopicEntity(surveyTopicDao.getId(), surveyTopicDao.getTitle(), surveyTopicDao.getProgress()));
                                        }
                                        Log.d("SurveyNetwork", result.size() + "");
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
        }).subscribeOn(Schedulers.io());
    }
}
