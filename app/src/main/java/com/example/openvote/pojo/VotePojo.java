package com.example.openvote.pojo;

import com.example.openvote.R;

import java.util.ArrayList;
import java.util.List;

public class VotePojo {
    private int profilePic;
    private String userName, date, seekbar, seekbartime, question, desition, comment;

    public VotePojo() {
    }

    public VotePojo(int profilePic, String userName, String date, String seekbar, String seekbartime, String question, String desition, String comment) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.date = date;
        this.seekbar = seekbar;
        this.seekbartime = seekbartime;
        this.question = question;
        this.desition = desition;
        this.comment = comment;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
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

    public String getDesition() {
        return desition;
    }

    public void setDesition(String desition) {
        this.desition = desition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static List<VotePojo> getVote(){
        List<VotePojo> votePojos = new ArrayList<>();
        votePojos.add(new VotePojo(R.drawable.heart_full, "Dip Mondol","9/01/20","","","Do you agree?","",""));
        votePojos.add(new VotePojo(R.drawable.heart_full, "Nahiyan","7/01/20","","","Do you agree?","",""));
        return votePojos;
    }
}
