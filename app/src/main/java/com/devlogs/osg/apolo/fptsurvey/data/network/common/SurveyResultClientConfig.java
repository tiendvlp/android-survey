package com.devlogs.osg.apolo.fptsurvey.data.network.common;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface SurveyResultClientConfig {
    @POST("survey/result/publishresult")
    Call<PublishSurveyResultRespondModel> publishResult (@HeaderMap Map<String, String> header, @Body PublishResultReqBody body);

    public static class PublishResultReqBody {
        private String surveyId;
        private List<Integer> answer;

        public PublishResultReqBody(String surveyId, List<Integer> answer) {
            this.surveyId = surveyId;
            this.answer = answer;
        }

        public String getSurveyId() {
            return surveyId;
        }

        public List<Integer> getAnswer() {
            return answer;
        }
    }

    public static class PublishSurveyResultRespondModel {
        String surveyResultId;
        Boolean isSuccess;

        public PublishSurveyResultRespondModel(String surveyResultId, Boolean isSuccess) {
            this.surveyResultId = surveyResultId;
            this.isSuccess = isSuccess;
        }

        public String getSurveyResultId() {
            return surveyResultId;
        }

        public void setSurveyResultId(String surveyResultId) {
            this.surveyResultId = surveyResultId;
        }

        public Boolean getSuccess() {
            return isSuccess;
        }

        public void setSuccess(Boolean success) {
            isSuccess = success;
        }
    }
}
