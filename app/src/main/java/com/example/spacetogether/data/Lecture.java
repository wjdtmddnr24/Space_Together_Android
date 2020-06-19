package com.example.spacetogether.data;

import java.io.Serializable;
import java.util.List;

public class Lecture implements Serializable {
    private String lectureName;
    private List<Schedule> schedule;

    public Lecture() {
    }

    public Lecture(String lectureName, List<Schedule> schedule) {
        this.lectureName = lectureName;
        this.schedule = schedule;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
