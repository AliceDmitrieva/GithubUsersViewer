package com.alisadmitrieva.githubusersviewer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private String TAG = Repository.class.getName();
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<GithubUser> githubUsers = new ArrayList<>();
    private int lastUserID;

    public void getGithubUsersFromServer() {
        APIService apiService = createAPIService();

        disposable.add(
                apiService.getUsers(lastUserID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<GithubUser>>() {
                                           @Override
                                           public void onNext(List<GithubUser> users) {
                                               githubUsers.clear();
                                               githubUsers.addAll(users);
                                               lastUserID = githubUsers.get(githubUsers.size() - 1).getId();
                                           }

                                           @Override
                                           public void onError(Throwable e) {
                                               Log.d(TAG, "Error: " + e.getMessage());
                                           }

                                           @Override
                                           public void onComplete() {

                                           }
                                       }
                        )
        );
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
