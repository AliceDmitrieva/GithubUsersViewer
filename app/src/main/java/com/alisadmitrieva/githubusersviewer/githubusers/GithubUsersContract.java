package com.alisadmitrieva.githubusersviewer.githubusers;

import com.alisadmitrieva.githubusersviewer.GithubUser;

import java.util.List;

public class GithubUsersContract {

    interface View {
        void showUsers(List<GithubUser> githubUsers);

        void refreshData(List<GithubUser> githubUsers);

        void showErrorMessage(String message);
    }

    interface Presenter {
        void attachView(GithubUsersContract.View view);

        void loadUsers(int lastUserId);

        void detachView();
    }

}
