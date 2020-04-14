package com.alisadmitrieva.githubusersviewer.githubusers;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.alisadmitrieva.githubusersviewer.GithubUser;
import com.alisadmitrieva.githubusersviewer.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GithubUsersContract.View {

    private ViewPager2 viewPager;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;
    private GithubUsersPresenter presenter;
    private int lastUserID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);

        presenter = new GithubUsersPresenter();
        presenter.attachView(this);
        presenter.loadUsers(lastUserID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        presenter.detachView();
    }

    @Override
    public void showUsers(List<GithubUser> githubUsers) {
        lastUserID = githubUsers.get(githubUsers.size() - 1).getId();

        GithubUsersViewPagerAdapter viewPagerAdapter = new GithubUsersViewPagerAdapter(MainActivity.this, githubUsers);
        viewPager.setAdapter(viewPagerAdapter);
        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position != 0) {
                    presenter.loadUsers(lastUserID);
                }
            }
        };
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
