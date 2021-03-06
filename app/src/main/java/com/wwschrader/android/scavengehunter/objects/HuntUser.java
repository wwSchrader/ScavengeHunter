package com.wwschrader.android.scavengehunter.objects;

import java.util.Map;

/**
 * Created by Warren on 12/13/2016.
 * Object representing users to be synced with Firebase
 */

public class HuntUser {

    private String username;
    private String email;
    private String participatingHunt;
    private String adminHunt;
    private Map<String, Boolean> accomplishedObjectives;



    //default constructor
    public HuntUser(){}

    public HuntUser(String username, String email){
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getParticipatingHunt() {
        return participatingHunt;
    }

    public void setParticipatingHunt(String participatingHunt) {
        this.participatingHunt = participatingHunt;
    }

    public Map<String, Boolean> getAccomplishedObjectives() {
        return accomplishedObjectives;
    }

    public void setAccomplishedObjectives(Map<String, Boolean> accomplishedObjectives) {
        this.accomplishedObjectives = accomplishedObjectives;
    }

    public String getAdminHunt() {
        return adminHunt;
    }

    public void setAdminHunt(String adminHunt) {
        this.adminHunt = adminHunt;
    }
}
