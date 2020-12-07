package com.devlogs.osg.apolo.fptsurvey.surveyresult;

import android.util.Log;

import com.devlogs.osg.apolo.fptsurvey.data.network.SurveyResultNetworkImp;
import com.devlogs.osg.apolo.fptsurvey.domain.data.AuthDisk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetSurveyResultLayoutUseCaseSync {
    private SurveyResultNetworkImp mNetwork;
    private AuthDisk mAuth;

    @Inject
    public GetSurveyResultLayoutUseCaseSync(SurveyResultNetworkImp mNetwork, AuthDisk mAuth) {
        this.mNetwork = mNetwork;
        this.mAuth = mAuth;
    }

    public Single<String> execute(String surveyResult) {
        return mAuth.getAccessToken().flatMap(new Function<String, SingleSource<String>>() {
                    @Override
                    public SingleSource<String> apply(String s) throws Throwable {
                        return mNetwork.getSurveyResult(surveyResult, s).map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Throwable {
                                Log.d("jsonraw", s);
                                return analyzingJson(s);
                            }
                        });
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    private String analyzingJson(String s) {
        String pattern = "(?<=\\\":\"\\$)(.*?)(?=\\\")";
        String lastResult = s;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        List<String[]> paths = new ArrayList();

        int pathIndex = 0;
        while (m.find()) {
            paths.add(m.group(pathIndex).split(Pattern.quote(".")));
            String value = getValue(s, paths.get(pathIndex));
            lastResult = lastResult.replaceFirst(Pattern.quote("$" + m.group(pathIndex)), value);
            pathIndex++;
        }

        return lastResult;
    }

    private String getValue(String s, String[] path) {
        try {
            JSONObject r2 = new JSONObject(s).getJSONObject("surveyresult");
            for (int i = 0; i < path.length; i++) {
                if (r2.get(path[i]) instanceof JSONObject) {
                    r2 = r2.getJSONObject(path[i]);
                    continue;
                } else if (r2.get(path[i]) instanceof JSONArray) {
                    JSONArray arr = r2.getJSONArray(path[i]);
                    if (arr.get(Integer.parseInt(path[i + 1])) instanceof JSONObject) {
                        r2 = arr.getJSONObject(Integer.parseInt(path[i] + 1));
                    } else {
                        return arr.getString(Integer.parseInt(path[i + 1]));
                    }
                    i = i + 1;
                } else {
                    return r2.getString(path[i]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("JSON error cc");
    }


}

