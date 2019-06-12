package space.flogiston.projectmanager.data.entities;

import java.util.ArrayList;
import java.util.List;

public class MyProjects {
    private String action;

    private ArrayList<ProjectsList> projectsList;

    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setAction(String action){
        this.action = action;
    }
    public String getAction() {
        return this.action;
    }
    public void setProjectsList(ArrayList<ProjectsList> projectsList){
        this.projectsList = projectsList;
    }
    public ArrayList<ProjectsList> getProjectsList(){
        return this.projectsList;
    }
}
