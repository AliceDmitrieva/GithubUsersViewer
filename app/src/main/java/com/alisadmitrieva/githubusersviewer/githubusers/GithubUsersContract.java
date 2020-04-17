package com.alisadmitrieva.githubusersviewer.githubusers;

import com.alisadmitrieva.githubusersviewer.GithubUser;

import java.util.List;

public class GithubUsersContract {

    interface View {
        void showUsers(int pageIndex, List<GithubUser> githubUsers);

        void showErrorMessage(String message);
    }

    interface Presenter {
        void attachView(GithubUsersContract.View view);

        void loadUsers(int postition, int lastUserId);

        void detachView();
    }

}
