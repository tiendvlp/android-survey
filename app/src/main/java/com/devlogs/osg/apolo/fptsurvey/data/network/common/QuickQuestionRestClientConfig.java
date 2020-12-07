package com.devlogs.osg.apolo.fptsurvey.data.network.common;


import com.devlogs.osg.apolo.fptsurvey.domain.data.model.QuickQuestionNetworkModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface QuickQuestionRestClientConfig {

    @GET("quickquestion/getall")
    Call<List<QuickQuestionNetworkModel>> getAllQuickQuestion();

    @POST("quickquestion/addNewResult")
    Call<AddNewResultRespondModel> addNewResult (@Body AddNewResultReqBody body);

    public static class AddNewResultReqBody {
        String questionId;
        int answer;
        String email;

        public AddNewResultReqBody(String questionId, int answer, String email) {
            this.questionId = questionId;
            this.answer = answer;
            this.email = email;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public int getAnswer() {
            return answer;
        }

        public void setAnswer(int answer) {
            this.answer = answer;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class AddNewResultRespondModel {
        String message;

        public AddNewResultRespondModel(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
