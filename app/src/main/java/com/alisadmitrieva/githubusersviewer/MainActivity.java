package com.alisadmitrieva.githubusersviewer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;
    private Repository repository;
    private List<GithubUser> githubUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        Repository repository = new Repository();

        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                repository.getGithubUsersFromServer();
            }
        };

        repository.getGithubUsersFromServer();
        GithubUsersViewPagerAdapter viewPagerAdapter = new GithubUsersViewPagerAdapter(MainActivity.this, githubUsers);
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        viewPager.setAdapter(viewPagerAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
    }

}
