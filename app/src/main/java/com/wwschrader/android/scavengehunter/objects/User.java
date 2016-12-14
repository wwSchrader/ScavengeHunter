package com.wwschrader.android.scavengehunter.objects;

/**
 * Created by Warren on 12/13/2016.
 * Object representing users to be synced with Firebase
 */

public class User {

    private String username;
    private String email;
    private String participatingHunt;


    //default constructor
    public User(){}

    public User(String username, String email){
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
}
