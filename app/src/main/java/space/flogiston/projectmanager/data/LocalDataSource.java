package space.flogiston.projectmanager.data;

import android.content.Context;

import java.util.ArrayList;

import androidx.room.Room;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.Err;
import space.flogiston.projectmanager.data.entities.MyColleagues;
import space.flogiston.projectmanager.data.entities.MyProjects;
import space.flogiston.projectmanager.data.entities.MyTasksList;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.data.entities.SubordinatesList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

public class LocalDataSource {
    private final ProjectManagerDB db;
    public LocalDataSource(Context context) {
        db = Room.databaseBuilder(context, ProjectManagerDB.class, "database").build();
    }

    public void storeMyProjects (ArrayList<ProjectsList> myProjects) {
        db.myProjectsDao().deleteAll();
        db.myProjectsDao().insertMyProjects(myProjects);
    }
    public ArrayList<ProjectsList> getMyProjects () {
        return (ArrayList<ProjectsList>)db.myProjectsDao().getMyProjects();
    }
    public void storeMyColleagues (ArrayList<ColleaguesList> myColleagues, int projectId) {
        db.myColleaguesDao().deleteColleagues(projectId);
        db.myColleaguesDao().insertMyColleagues(myColleagues);
    }
    public ArrayList<ColleaguesList> getMyColleagues (int projectId) {
        return (ArrayList<ColleaguesList>)db.myColleaguesDao().getMyColleagues(projectId);
    }
    public void storeMySubordinates (ArrayList<SubordinatesList> mySubordinates, int projectId) {
        db.mySubordinatesDao().deleteSubordinates(projectId);
        db.mySubordinatesDao().insertMySubordinates(mySubordinates);
    }
    public ArrayList<SubordinatesList> getMySubordinates (int projectId) {
        return (ArrayList<SubordinatesList>)db.mySubordinatesDao().getMySubordinates(projectId);
    }
    public void storeMyTasks (ArrayList<MyTasksList> myTasks, int projectId) {
        db.myTasksDao().deleteMyTasks(projectId);
        db.myTasksDao().insertMyTasks(myTasks);
    }
    public ArrayList<MyTasksList> getMyTasks (int projectId) {
        return (ArrayList<MyTasksList>)db.myTasksDao().getMyTasks(projectId);
    }
    public void storeSubordinatesTasks (ArrayList<SubordinatesTasksList> subordinatesTasks, int projectId) {
        db.subordinatesTasksDao().deleteSubordinatesTasks(projectId);
        db.subordinatesTasksDao().insertSubordinatesTasks(subordinatesTasks);
    }
    public ArrayList<SubordinatesTasksList> getSubordinatesTasks (int projectId) {
        return (ArrayList<SubordinatesTasksList>)db.subordinatesTasksDao()
                .getSubordinatesTasks(projectId);
    }
    public ArrayList<Err> getErrors () {
        return (ArrayList<Err>)db.errorsDao().getErrors();
    }
    public void storeError (Err err) {
        db.errorsDao().insertError(err);
    }
    public void deleteErrors () { db.errorsDao().deleteAll(); }

    public void exit () {
        db.myProjectsDao().deleteAll();
        db.myColleaguesDao().deleteAll();
        db.mySubordinatesDao().deleteAll();
        db.myTasksDao().deleteAll();
        db.subordinatesTasksDao().deleteAll();
    }
}
