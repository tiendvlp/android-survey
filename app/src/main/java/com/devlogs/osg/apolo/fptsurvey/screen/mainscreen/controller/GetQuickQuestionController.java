package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;


import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.domain.entity.QuickQuestionEntity;
import com.devlogs.osg.apolo.fptsurvey.quickquestion.GetAllQuickQuestionUseCase;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationStateStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

public class GetQuickQuestionController {
    private GetAllQuickQuestionUseCase mGetAllQuickQuestionUseCase;
    private MainScreenPresentationStateStore mStateStore;

    @Inject
    public GetQuickQuestionController(GetAllQuickQuestionUseCase GetAllQuickQuestionUseCase, MainScreenPresentationStateStore mainStateStore) {
        mGetAllQuickQuestionUseCase = GetAllQuickQuestionUseCase;
        mStateStore = mainStateStore;
    }

    public void getAllQuickQuestion(String userEmail) {
        mGetAllQuickQuestionUseCase.execute()

                .map(new Function<List<QuickQuestionEntity>, List<QuickQuestionPM>>() {
                    @Override
                    public List<QuickQuestionPM> apply(List<QuickQuestionEntity> quickQuestionEntities) throws Throwable {
                        List<QuickQuestionPM> result = new ArrayList();

                        Root:
                        for (QuickQuestionEntity quickQuestionEntity : quickQuestionEntities) {
                            for (List<String> strings : quickQuestionEntity.getUserAnswer()) {
                                for (String string : strings) {
                                    if (string.equals(userEmail)) {
                                        continue Root;
                                    };
                                }
                            }
                            Log.d("GetQuick", "id: " + quickQuestionEntity.getId());
                            result.add(new QuickQuestionPM(quickQuestionEntity.getId(),quickQuestionEntity.getQuestion(), quickQuestionEntity.getAnswer()));
                        }
                        return result;
                    }
                })
                .subscribe(new SingleObserver<List<QuickQuestionPM>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<QuickQuestionPM> quickQuestionPMS) {
                        mStateStore.updateEffect(new MainScreenPresentationState.MainScreenPresentationEffect.QuickQuestionLoaded(quickQuestionPMS));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mStateStore.updateState(new MainScreenPresentationState.Error(e.getMessage()));
                    }
                });
        ;

    }
}
