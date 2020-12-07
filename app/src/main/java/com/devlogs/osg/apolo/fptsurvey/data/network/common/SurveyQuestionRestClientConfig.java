package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Url;

public interface SurveyQuestionRestClientConfig {
    @GET()
    Call<List<GetAllQuestionResBody>> getAllQuestionInSurvey (@Url String url, @HeaderMap Map<String, String> header);

    public static class GetAllQuestionResBody {
        public String question;
        public String id;
        public String surveyId;
        public String[] answer;

        public GetAllQuestionResBody(String question, String id, String surveyId, String[] answer) {
            this.question = question;
            this.id = id;
            this.surveyId = surveyId;
            this.answer = answer;
        }
    }
}
