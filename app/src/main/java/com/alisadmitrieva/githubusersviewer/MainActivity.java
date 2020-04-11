package com.alisadmitrieva.githubusersviewer;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getName();
    private CompositeDisposable disposable = new CompositeDisposable();
    private APIService apiService;
    private List<GithubUser> githubUsers = new ArrayList<>();

    private ViewPager2 viewPager;
    private GithubUsersViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);

        apiService = createAPIService();
        getGithubUsersFromServer();
    }

    private APIService createAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIService.class);
    }

    private void getGithubUsersFromServer() {
        disposable.add(
                apiService.getUsers(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<GithubUser>>() {
                                           @Override
                                           public void onNext(List<GithubUser> users) {
                                               githubUsers.addAll(users);
                                               viewPagerAdapter = new GithubUsersViewPagerAdapter(MainActivity.this, githubUsers);
                                               viewPager.setAdapter(viewPagerAdapter);
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

}
