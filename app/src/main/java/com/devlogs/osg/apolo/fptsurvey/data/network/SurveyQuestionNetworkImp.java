package com.devlogs.osg.apolo.fptsurvey.data.network;

import com.devlogs.osg.apolo.fptsurvey.data.network.common.SurveyQuestionRestClientConfig;
import com.devlogs.osg.apolo.fptsurvey.domain.data.SurveyQuestionNetwork;
import com.devlogs.osg.apolo.fptsurvey.domain.data.model.SurveyQuestionNetworkModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SurveyQuestionNetworkImp implements SurveyQuestionNetwork {
    private Retrofit mRetrofit;

    @Inject
    public SurveyQuestionNetworkImp(Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    @Override
    public Single<List<SurveyQuestionNetworkModel>> getSurveyQuestion(String idToken, String id) {
        return Single.create(new SingleOnSubscribe<List<SurveyQuestionNetworkModel>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<SurveyQuestionNetworkModel>> emitter) throws Throwable {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Authorization","Bearer " + idToken);
                SurveyQuestionRestClientConfig client = mRetrofit.create(SurveyQuestionRestClientConfig.class);
                client.getAllQuestionInSurvey("question/allquestioninsurvey?surveyId=" + id, header)
                        .enqueue(new Callback<List<SurveyQuestionRestClientConfig.GetAllQuestionResBody>>() {
                            @Override
                            public void onResponse(Call<List<SurveyQuestionRestClientConfig.GetAllQuestionResBody>> call, Response<List<SurveyQuestionRestClientConfig.GetAllQuestionResBody>> response) {
                                if (response.code() == 200) {
                                    ArrayList<SurveyQuestionNetworkModel> result = new ArrayList();
                                    for (SurveyQuestionRestClientConfig.GetAllQuestionResBody question : response.body()) {
                                        result.add(new SurveyQuestionNetworkModel(question.id, question.question, question.answer));
                                    }
                                    emitter.onSuccess(result);
                                } else {
                                    emitter.onError(new Exception("ErrorCode: "+ response.code()));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<SurveyQuestionRestClientConfig.GetAllQuestionResBody>> call, Throwable t) {
                                    emitter.onError(t);
                            }
                        });
            }
        });
    }
}
