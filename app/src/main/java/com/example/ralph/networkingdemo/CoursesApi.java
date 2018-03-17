package com.example.ralph.networkingdemo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ralph on 17/03/18.
 */

public interface CoursesApi {

    @GET("courses.json")
    Call<CourseResponse> getCourseResponse();

    @GET("users/{uname}")
    Call<GithubProfile> getGithubProfile(@Path("uname") String username);

    @GET("users/{uname}/repos")
    Call<ArrayList<Repo>> getRepos(@Path("uname") String username);

    @GET("https://api.github.com/users/{uname}/followers")
    Call<ArrayList<GithubProfile>> getFollowers(@Path("uname") String username, @Query("user_id") int userId,@Query("abcd") String abcd);
}
