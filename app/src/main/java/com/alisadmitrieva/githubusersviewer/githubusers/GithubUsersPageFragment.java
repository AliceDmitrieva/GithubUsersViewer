package com.alisadmitrieva.githubusersviewer.githubusers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.alisadmitrieva.githubusersviewer.GithubUser;
import com.alisadmitrieva.githubusersviewer.R;

import java.util.ArrayList;
import java.util.List;

public class GithubUsersPageFragment extends Fragment {

    private static String ARGUMENT_USERS_LIST = "users";

    public static GithubUsersPageFragment newInstance(List<GithubUser> githubUsers) {
        GithubUsersPageFragment githubUsersPageFragment = new GithubUsersPageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_USERS_LIST, new ArrayList<>(githubUsers));
        githubUsersPageFragment.setArguments(bundle);
        return githubUsersPageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.github_users_page_fragment, container);
        GridView gridView = view.findViewById(R.id.gridview);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList<GithubUser> githubUsers = arguments.getParcelableArrayList(ARGUMENT_USERS_LIST);
        if (githubUsers == null) {
            throw new IllegalArgumentException("githubUsers == null");
        }

        GithubUsersAdapter githubUsersAdapter = new GithubUsersAdapter(getActivity(), githubUsers);
        gridView.setAdapter(githubUsersAdapter);
        return view;
    }

}
