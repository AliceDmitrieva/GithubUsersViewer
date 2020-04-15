package com.alisadmitrieva.githubusersviewer.githubusers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alisadmitrieva.githubusersviewer.GithubUser;
import com.alisadmitrieva.githubusersviewer.R;

import java.util.List;

public class GithubUsersViewPagerAdapter extends RecyclerView.Adapter<GithubUsersViewPagerAdapter.GithubUsersViewHolder> {

    private AppCompatActivity activity;
    private List<GithubUser> githubUsers;

    public GithubUsersViewPagerAdapter(AppCompatActivity activity, List<GithubUser> githubUsers) {
        this.activity = activity;
        this.githubUsers = githubUsers;
    }

    @NonNull
    @Override
    public GithubUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_users_page_fragment, parent, false);
        return new GithubUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUsersViewHolder holder, int position) {
        holder.bindGithubUsers();
    }

    @Override
    public int getItemCount() {
        return githubUsers.size();
    }

    class GithubUsersViewHolder extends RecyclerView.ViewHolder {

        GridView gridView;

        GithubUsersViewHolder(@NonNull View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.gridview);
        }

        void bindGithubUsers() {
            GithubUsersAdapter githubUsersAdapter = new GithubUsersAdapter(activity, githubUsers);
            gridView.setAdapter(githubUsersAdapter);
        }
    }

}
