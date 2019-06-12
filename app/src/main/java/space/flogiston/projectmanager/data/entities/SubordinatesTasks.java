package space.flogiston.projectmanager.data.entities;

import java.util.ArrayList;

public class SubordinatesTasks {
    private String action;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private int projectId;

    private ArrayList<SubordinatesTasksList> tasksList;

    private String error;

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
    public void setTasksList(ArrayList<SubordinatesTasksList> tasksList){
        this.tasksList = tasksList;
    }
    public ArrayList<SubordinatesTasksList> getTasksList(){
        return this.tasksList;
    }
}
