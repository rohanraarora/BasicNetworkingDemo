package com.example.ralph.networkingdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ralph on 17/03/18.
 */

public class GithubProfile {


    String name;
    String company;
    @SerializedName("avatar_url")
    String avatarUrl;
}
