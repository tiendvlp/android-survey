package com.devlogs.osg.apolo.fptsurvey.status;

import com.devlogs.osg.apolo.fptsurvey.domain.data.StatusNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.StatusNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.StatusEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAllStatusUseCase {
    private StatusNetwork mStatusNetwork;

    @Inject
    public GetAllStatusUseCase (StatusNetwork statusNetwork) {
        mStatusNetwork = statusNetwork;
    }

    public Single<List<StatusEntity>> getAllStatus () {
        return mStatusNetwork.getAllStatus().map(new Function<List<StatusNetworkModel>, List<StatusEntity>>() {
            @Override
            public List<StatusEntity> apply(List<StatusNetworkModel> statusNetworkModels) throws Throwable {
                List<StatusEntity> result = new ArrayList();

                for (StatusNetworkModel statusNetworkModel : statusNetworkModels) {
                    result.add(new StatusEntity(statusNetworkModel.getId(), statusNetworkModel.getType(), statusNetworkModel.getContent()));
                }

                return result;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }
}
