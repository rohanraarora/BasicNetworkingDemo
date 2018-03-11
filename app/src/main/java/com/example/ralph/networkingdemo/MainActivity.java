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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayAdapter<String> adapter;
    ArrayList<String> courseNames = new ArrayList<>();

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
        String urlString = "https://codingninjas.in/api/v2/courses.json";
        CoursesAsyncTask asyncTask = new CoursesAsyncTask(new CoursesAsyncTask.CoursesDownloadListener() {
            @Override
            public void onDownloadComplete(ArrayList<Course> courses) {
                if(courses!= null){
                    for(int i = 0;i<courses.size();i++){
                        Course course = courses.get(i);
                        courseNames.add(course.getName());
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    Snackbar.make(listView,"Try Again.",Snackbar.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });
        asyncTask.execute(urlString);
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
