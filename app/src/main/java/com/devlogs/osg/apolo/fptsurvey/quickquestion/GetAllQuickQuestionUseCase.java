package com.devlogs.osg.apolo.fptsurvey.quickquestion;

import com.devlogs.osg.apolo.fptsurvey.domain.data.QuickQuestionNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.QuickQuestionNetworkModel;
import com.devlogs.osg.apolo.fptsurvey.domain.entity.QuickQuestionEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

public class GetAllQuickQuestionUseCase {
    private QuickQuestionNetwork quickQuestionNetwork;

    @Inject
    public GetAllQuickQuestionUseCase(QuickQuestionNetwork quickQuestionNetwork) {
        this.quickQuestionNetwork = quickQuestionNetwork;
    }

    public Single<List<QuickQuestionEntity>> execute () {
        return quickQuestionNetwork.getAllQuickQuestion().map(new Function<List<QuickQuestionNetworkModel>, List<QuickQuestionEntity>>() {
            @Override
            public List<QuickQuestionEntity> apply(List<QuickQuestionNetworkModel> quickQuestionNetworkModels) throws Throwable {
                List<QuickQuestionEntity> result = new ArrayList<>();

                for (QuickQuestionNetworkModel quickQuestionNetworkModel : quickQuestionNetworkModels) {
                    result.add(new QuickQuestionEntity(
                            quickQuestionNetworkModel.getId(),
                            quickQuestionNetworkModel.getQuestion(),
                            quickQuestionNetworkModel.getAnswer(),
                            quickQuestionNetworkModel.getUserAnswer()));
                }

                return result;
            }
        });
    }
}
