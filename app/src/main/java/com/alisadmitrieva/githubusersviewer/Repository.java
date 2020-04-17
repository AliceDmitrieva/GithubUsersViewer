package com.alisadmitrieva.githubusersviewer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    public Observable<List<GithubUser>> getGithubUsersFromServer(int lastUserID) {
        APIService apiService = createAPIService();
        return apiService.getUsers(lastUserID);
    }

    private APIService createAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIService.class);
    }

}
