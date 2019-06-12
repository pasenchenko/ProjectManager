package space.flogiston.projectmanager;

import android.app.Application;

import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.ProjectsList;

public class ProjectManager extends Application {

    private Repository repository;
    private ProjectsList currentProject;

    public ProjectsList getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(ProjectsList currentProject) {
        this.currentProject = currentProject;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(this);
    }

    public Repository getRepository() {
        return repository;
    }
}
