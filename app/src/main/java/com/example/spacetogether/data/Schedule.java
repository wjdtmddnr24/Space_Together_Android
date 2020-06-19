package com.example.spacetogether.data;

import java.io.Serializable;
import java.util.Date;

public class Schedule implements Serializable {
    private Date startDate;
    private Date endDate;

    public Schedule() {
    }

    public Schedule(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
