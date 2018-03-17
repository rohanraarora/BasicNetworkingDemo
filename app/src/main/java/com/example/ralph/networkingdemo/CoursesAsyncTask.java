package com.example.ralph.networkingdemo;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ralph on 11/03/18.
 */

public class CoursesAsyncTask extends AsyncTask<String,Void,ArrayList<Course>> {

    public interface CoursesDownloadListener{


        void onDownloadComplete(ArrayList<Course> courses);
    }

    private CoursesDownloadListener mListener;

    CoursesAsyncTask(CoursesDownloadListener listener){
        mListener = listener;
    }


    @Override
    protected ArrayList<Course> doInBackground(String... strings) {
        String urlString = strings[0];
        try {
            URL url = new URL(urlString);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");

            httpsURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();

            String result = "";
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()){
                result = result.concat(scanner.next());
            }

            ArrayList<Course> courses = parseCourses(result);

            Log.d("Response",result);

            httpsURLConnection.disconnect();

            return courses;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Course> parseCourses(String result) throws JSONException {

        Gson gson = new Gson();
        CourseResponse courseResponse = gson.fromJson(result,CourseResponse.class);
        return courseResponse.data.courses;
//        ArrayList<Course> courses = new ArrayList<>();
//        JSONObject rootObject = new JSONObject(result);
//        JSONObject data = rootObject.getJSONObject("data");
//        JSONArray coursesJSONArray = data.getJSONArray("courses");
//
//        for(int i = 0;i<coursesJSONArray.length();i++){
//            JSONObject courseObject = coursesJSONArray.getJSONObject(i);
//
//            Course course = gson.fromJson(courseObject.toString(),Course.class);
////            int id = courseObject.getInt("id");
////            String title = courseObject.getString("title");
////            String name = courseObject.getString("name");
////            String overview = courseObject.getString("overview");
////            Course course = new Course(id,title,name,overview);
//            courses.add(course);
//        }
//        return courses;

    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected void onPostExecute(ArrayList<Course> courses) {
        super.onPostExecute(courses);
        mListener.onDownloadComplete(courses);
    }
}
