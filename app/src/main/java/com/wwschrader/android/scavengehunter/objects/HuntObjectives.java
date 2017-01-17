package com.wwschrader.android.scavengehunter.objects;

import java.util.Map;

/**
 * Created by Warren on 12/13/2016.
 * Object for hunt objectives to sync with firebase.
 */

public class HuntObjectives {

    private String objectiveName;
    private String objectiveDescription;
    private int objectivePoints;
    private Map<String, Boolean> accomplishedUsers;

    public HuntObjectives(){}

    public HuntObjectives(String objectiveName, String objectiveDescription, int points) {
        this.objectiveName = objectiveName;
        this.objectiveDescription = objectiveDescription;
        this.objectivePoints = points;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public String getObjectiveDescription() {
        return objectiveDescription;
    }

    public void setObjectiveDescription(String objectiveDescription) {
        this.objectiveDescription = objectiveDescription;
    }

    public int getPoints() {
        return objectivePoints;
    }

    public void setPoints(int points) {
        this.objectivePoints = points;
    }

    public Map<String, Boolean> getAccomplishedUsers() {
        return accomplishedUsers;
    }

    public void setAccomplishedUsers(Map<String, Boolean> accomplishedUsers) {
        this.accomplishedUsers = accomplishedUsers;
    }
}
