package com.alisadmitrieva.githubusersviewer.githubusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alisadmitrieva.githubusersviewer.GithubUser;

import java.util.List;

public class GithubUsersViewPagerAdapter extends FragmentStateAdapter {

    private AppCompatActivity activity;
    private List<GithubUser> githubUsers;

    public GithubUsersViewPagerAdapter(AppCompatActivity activity, List<GithubUser> githubUsers) {
        super(activity);
        this.activity = activity;
        this.githubUsers = githubUsers;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return GithubUsersPageFragment.newInstance(githubUsers);
    }

    @Override
    public int getItemCount() {
        return githubUsers.size();
    }

}
