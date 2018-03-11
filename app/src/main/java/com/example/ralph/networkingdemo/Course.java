package com.example.ralph.networkingdemo;

/**
 * Created by ralph on 11/03/18.
 */

public class Course {

    private int id;
    private String title;
    private String name;
    private String overview;

    public Course(int id, String title, String name, String overview) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
