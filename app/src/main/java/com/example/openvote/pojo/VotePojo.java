package com.example.openvote.pojo;

import java.util.ArrayList;
import java.util.List;

public class VotePojo {
    private String profilePic, userName, date, seekbar, seekbartime, question, agree, disagree, comment;

    public VotePojo() {
    }

    public VotePojo(String profilePic, String userName, String date, String seekbar, String seekbartime, String question, String agree, String disagree, String comment) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.date = date;
        this.seekbar = seekbar;
        this.seekbartime = seekbartime;
        this.question = question;
        this.agree = agree;
        this.disagree = disagree;
        this.comment = comment;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeekbar() {
        return seekbar;
    }

    public void setSeekbar(String seekbar) {
        this.seekbar = seekbar;
    }

    public String getSeekbartime() {
        return seekbartime;
    }

    public void setSeekbartime(String seekbartime) {
        this.seekbartime = seekbartime;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getDisagree() {
        return disagree;
    }

    public void setDisagree(String disagree) {
        this.disagree = disagree;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
