package com.pdk.manage.dto.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.sm.EmployeeReview;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by liuhaiming on 2015/8/14.
 */
public class EmployeeReviewJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private int index;

    private double score;

    private String scoreStar;

    private String userId;

    private String userName;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    public EmployeeReviewJson(int index, EmployeeReview employeeReview) {
        this.id = employeeReview.getId();
        this.index = index;
        this.score = employeeReview.getScore();
        this.scoreStar = "<div class='employeeReviewStar' score='" + employeeReview.getScore() + "'></div>";
        this.description = employeeReview.getDescription();
        this.userId = employeeReview.getUserId();
        this.ts = employeeReview.getTs();
        this.dr = employeeReview.getDr();

        if ( employeeReview.getUser() != null ) {
            this.userName = employeeReview.getUser().getName();
        }

    }

    public EmployeeReviewJson() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Short getDr() {
        return dr;
    }

    public void setDr(Short dr) {
        this.dr = dr;
    }

    public String getScoreStar() {
        return scoreStar;
    }

    public void setScoreStar(String scoreStar) {
        this.scoreStar = scoreStar;
    }
}
