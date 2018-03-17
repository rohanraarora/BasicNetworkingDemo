package com.example.ralph.networkingdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubProfileActivity extends AppCompatActivity implements Callback<GithubProfile> {

    ImageView avatar;
    TextView name;
    TextView company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        company = findViewById(R.id.company);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fetchProfile("abcd");
            }
        });
    }

    private void fetchProfile(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoursesApi coursesApi = retrofit.create(CoursesApi.class);

        Call<GithubProfile> call = coursesApi.getGithubProfile(username);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<GithubProfile> call, Response<GithubProfile> response) {

        GithubProfile profile = response.body();
        name.setText(profile.name);
        company.setText(profile.company);
        Picasso.get().load(profile.avatarUrl).placeholder(R.drawable.ic_launcher_background).into(avatar);


    }

    @Override
    public void onFailure(Call<GithubProfile> call, Throwable t) {

    }
}
