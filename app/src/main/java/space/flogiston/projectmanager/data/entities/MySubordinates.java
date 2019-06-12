package space.flogiston.projectmanager.data.entities;

import java.util.ArrayList;

public class MySubordinates {
    private String action;

    private int projectId;

    private ArrayList<SubordinatesList> subordinatesList;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setAction(String action){
        this.action = action;
    }
    public String getAction(){
        return this.action;
    }
    public void setProjectId(int projectId){
        this.projectId = projectId;
    }
    public int getProjectId(){
        return this.projectId;
    }
    public void setSubordinatesList(ArrayList<SubordinatesList> subordinatesList){
        this.subordinatesList = subordinatesList;
    }
    public ArrayList<SubordinatesList> getSubordinatesList(){
        return this.subordinatesList;
    }
}
