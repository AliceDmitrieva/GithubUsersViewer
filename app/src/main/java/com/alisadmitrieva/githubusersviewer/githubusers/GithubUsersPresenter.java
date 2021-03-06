package com.alisadmitrieva.githubusersviewer.githubusers;

import com.alisadmitrieva.githubusersviewer.GithubUser;
import com.alisadmitrieva.githubusersviewer.Repository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GithubUsersPresenter implements GithubUsersContract.Presenter {

    private GithubUsersContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void attachView(GithubUsersContract.View view) {
        this.view = view;
    }

    @Override
    public void loadUsers(int pageIndex, int lastUserID) {
        Repository repository = new Repository();

        disposable.add(
                repository.getGithubUsersFromServer(lastUserID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<GithubUser>>() {
                                           @Override
                                           public void onNext(List<GithubUser> users) {
                                               view.showUsers(pageIndex, users);
                                           }

                                           @Override
                                           public void onError(Throwable e) {
                                               view.showErrorMessage(e.getMessage());
                                           }

                                           @Override
                                           public void onComplete() {

                                           }
                                       }
                        )
        );
    }

    @Override
    public void detachView() {
        view = null;
        disposable.clear();
        disposable.dispose();
    }

}
