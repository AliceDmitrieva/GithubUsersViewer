package com.alisadmitrieva.githubusersviewer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    public String BASE_URL = "https://api.github.com/";

    @GET("users")
    Observable<List<GithubUser>> getUsers(@Query("since") int lastUserID);

}
