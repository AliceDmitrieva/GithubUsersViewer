package com.alisadmitrieva.githubusersviewer.githubusers;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.alisadmitrieva.githubusersviewer.GithubUser;
import com.alisadmitrieva.githubusersviewer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GithubUsersContract.View {

    private ViewPager2 viewPager;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;
    private GithubUsersViewPagerAdapter viewPagerAdapter;
    private GithubUsersPresenter presenter;
    private int lastUserID = 0;
    private List<GithubUser> users = new ArrayList<>();
    private Map<Integer, List<GithubUser>> pages = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        presenter = new GithubUsersPresenter();
        presenter.attachView(this);
        setupViewPager();
    }

    private void setupViewPager() {
        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // if map doesn't contain position - load data from server
                // if map contains position - get data from map
                if (pages.containsKey(position)) {
                    users.clear();
                    users.addAll(pages.get(position));
                    viewPagerAdapter.notifyDataSetChanged();
                } else {
                    presenter.loadUsers(position, lastUserID);
                }
            }
        };
        int pageId = viewPager.getCurrentItem();
        onPageChangeCallback.onPageSelected(pageId);
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
    }

    @Override
    public void showUsers(int position, List<GithubUser> githubUsers) {
        lastUserID = getLastUserID(githubUsers);
        users.clear();
        users.addAll(githubUsers);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new GithubUsersViewPagerAdapter(MainActivity.this, users);
            viewPager.setAdapter(viewPagerAdapter);
        } else {
            viewPagerAdapter.notifyDataSetChanged();
        }
        pages.put(position, githubUsers);
    }

    private int getLastUserID(List<GithubUser> githubUsers) {
        return githubUsers.get(githubUsers.size() - 1).getId();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        presenter.detachView();
    }

}
