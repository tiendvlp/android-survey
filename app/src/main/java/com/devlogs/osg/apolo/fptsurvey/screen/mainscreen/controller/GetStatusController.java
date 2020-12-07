package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import com.devlogs.osg.apolo.fptsurvey.domain.entity.StatusEntity;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.StatusPM;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationState;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.screenstate.MainScreenPresentationStateStore;
import com.devlogs.osg.apolo.fptsurvey.status.GetAllStatusUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

public class GetStatusController {
    private GetAllStatusUseCase mGetAllStatusUseCase;
    private MainScreenPresentationStateStore mStateStore;


    @Inject
    public GetStatusController(GetAllStatusUseCase getAllStatusUseCase, MainScreenPresentationStateStore mainStateStore) {
        mGetAllStatusUseCase = getAllStatusUseCase;
        mStateStore = mainStateStore;
    }

    public void getAllStatus () {
         mGetAllStatusUseCase.getAllStatus()

                .map(new Function<List<StatusEntity>, List<StatusPM>>() {
                    @Override
                    public List<StatusPM> apply(List<StatusEntity> statusEntities) throws Throwable {
                        List<StatusPM> result = new ArrayList();
                        for (StatusEntity statusEntity : statusEntities) {
                            result.add(new StatusPM(statusEntity.getType(), statusEntity.getContent()));
                        }
                        return result;
                    }
                })
                .subscribe(new SingleObserver<List<StatusPM>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<StatusPM> statusPMS) {
                        mStateStore.updateEffect(new MainScreenPresentationState.MainScreenPresentationEffect.StatusLoaded(statusPMS));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mStateStore.updateState(new MainScreenPresentationState.Error(e.getMessage()));
                    }
                });
    }
}
