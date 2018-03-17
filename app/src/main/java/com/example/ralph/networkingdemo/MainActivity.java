package com.example.ralph.networkingdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayAdapter<String> adapter;
    ArrayList<String> courseNames = new ArrayList<>();
    ArrayList<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.listview);
        progressBar = findViewById(R.id.progressBar);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courseNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = courseList.get(position);
                Log.d("Json Response",course.toJson());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fetchCourses();
            }
        });
    }

    private void fetchCourses() {

        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
//        String urlString = "https://codingninjas.in/api/v2/courses.json";
//        CoursesAsyncTask asyncTask = new CoursesAsyncTask(new CoursesAsyncTask.CoursesDownloadListener() {
//            @Override
//            public void onDownloadComplete(ArrayList<Course> courses) {
//                if(courses!= null){
//                    courseList = courses;
//                    for(int i = 0;i<courses.size();i++){
//                        Course course = courses.get(i);
//                        courseNames.add(course.getCourseName());
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//                else {
//                    Snackbar.make(listView,"Try Again.",Snackbar.LENGTH_LONG).show();
//                }
//                progressBar.setVisibility(View.GONE);
//                listView.setVisibility(View.VISIBLE);
//            }
//        });
//        asyncTask.execute(urlString);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://codingninjas.in/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoursesApi coursesApi = retrofit.create(CoursesApi.class);

        Call<CourseResponse> call = coursesApi.getCourseResponse();

        call.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                CourseResponse courseResponse = response.body();
                ArrayList<Course> courses = courseResponse.data.courses;
                if(courses!= null){
                    courseList = courses;
                    for(int i = 0;i<courses.size();i++){
                        Course course = courses.get(i);
                        courseNames.add(course.getCourseName());
                    }
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                Log.d("Response Failed",t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
