package com.alisadmitrieva.githubusersviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GithubUsersAdapter extends BaseAdapter {

    private final Context context;
    private final List<GithubUser> githubUsers;

    public GithubUsersAdapter(Context context, List<GithubUser> githubUsers) {
        this.context = context;
        this.githubUsers = githubUsers;
    }

    @Override
    public int getCount() {
        return githubUsers.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GithubUser user = githubUsers.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.cell, null);
        }

        final ImageView userAvatarImageView = convertView.findViewById(R.id.userAvatar);
        final TextView userNameTextView = convertView.findViewById(R.id.userName);

        Picasso.get().load(user.getAvatarUrl()).into(userAvatarImageView);
        userNameTextView.setText(user.getLogin());

        return convertView;
    }

}
