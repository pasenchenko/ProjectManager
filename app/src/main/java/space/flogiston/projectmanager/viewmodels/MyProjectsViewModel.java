package space.flogiston.projectmanager.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.CreateProject;
import space.flogiston.projectmanager.data.entities.Login;
import space.flogiston.projectmanager.data.entities.MyInfo;
import space.flogiston.projectmanager.data.entities.MyProjects;

public class MyProjectsViewModel extends ViewModel {
    private Repository repository;
    MutableLiveData<MyInfo> myInfoLiveData;
    MutableLiveData<MyProjects> myProjectsLiveData;
    MutableLiveData<CreateProject> createProjectLiveData;
    public MyProjectsViewModel (Repository repository) {
        this.repository = repository;
    }
    public void requestMyInfo (String token) {
        myInfoLiveData = repository.getMyInfo(token);
    }
    public MutableLiveData<MyInfo> getMyInfo () {
        return myInfoLiveData;
    }
    public void requestMyProjects (String token) {
        myProjectsLiveData = repository.getMyProjects(token);
    }
    public MutableLiveData<MyProjects> getMyProjects () {
        return myProjectsLiveData;
    }
    public void requestCreateProject (String name, String description, String myRole, String token) {
        createProjectLiveData = repository.createProject(name, description, myRole, token);
    }
    public MutableLiveData<CreateProject> createProject () { return createProjectLiveData; }
    public void exit () {
        repository.exit();
    }
}
