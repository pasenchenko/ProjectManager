package space.flogiston.projectmanager.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.CreateProject;
import space.flogiston.projectmanager.data.entities.MyColleagues;
import space.flogiston.projectmanager.data.entities.MyInfo;
import space.flogiston.projectmanager.data.entities.MyProjects;

public class ColleaguesListViewModel extends ViewModel {
    private Repository repository;
    MutableLiveData<MyColleagues> myColleaguesLiveData;
    public ColleaguesListViewModel (Repository repository) {
        this.repository = repository;
    }
    public void requestMyColleagues (int projectId, String token) {
        myColleaguesLiveData = repository.getMyColleagues(projectId, token);
    }
    public MutableLiveData<MyColleagues> getMyColleagues () {
        return myColleaguesLiveData;
    }
}
