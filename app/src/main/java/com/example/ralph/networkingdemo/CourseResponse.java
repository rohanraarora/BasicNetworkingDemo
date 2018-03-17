package com.example.ralph.networkingdemo;

import java.util.ArrayList;

/**
 * Created by ralph on 17/03/18.
 */

public class CourseResponse {
    String message;
    int status;
    Data data;


    class Data {
        ArrayList<Course> courses;
    }
}
