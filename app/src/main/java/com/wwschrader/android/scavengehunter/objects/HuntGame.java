package com.wwschrader.android.scavengehunter.objects;

import java.util.Map;

/**
 * Created by Warren on 12/13/2016.
 * Object representing hunt games to be synced on Firebase
 */

public class HuntGame {

    private String huntName;
    private String huntPassword;
    private long huntStartTime;
    private long huntEndingTime;
    private String userUid;
    private Map<String, Boolean> huntPlayers;

    public HuntGame(){}

    public HuntGame(String huntName, String huntPassword, long huntStartTime, long huntEndingTime, String userUid) {

        this.huntName = huntName;
        this.huntPassword = huntPassword;
        this.huntStartTime = huntStartTime;
        this.huntEndingTime = huntEndingTime;
        this.userUid = userUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getHuntName() {
        return huntName;
    }

    public void setHuntName(String huntName) {
        this.huntName = huntName;
    }

    public String getHuntPassword() {
        return huntPassword;
    }

    public void setHuntPassword(String huntPassword) {
        this.huntPassword = huntPassword;
    }

    public long getHuntStartTime() {
        return huntStartTime;
    }

    public void setHuntStartTime(long huntStartTime) {
        this.huntStartTime = huntStartTime;
    }

    public long getHuntEndingTime() {
        return huntEndingTime;
    }

    public void setHuntEndingTime(long huntEndingTime) {
        this.huntEndingTime = huntEndingTime;
    }

    public Map<String, Boolean> getHuntPlayers() {
        return huntPlayers;
    }

    public void setHuntPlayers(Map<String, Boolean> huntPlayers) {
        this.huntPlayers = huntPlayers;
    }
}
