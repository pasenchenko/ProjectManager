package space.flogiston.projectmanager.viewmodels;

import androidx.lifecycle.ViewModel;
import space.flogiston.projectmanager.data.Repository;

public class ProjectViewModel extends ViewModel {
    private Repository repository;

    public ProjectViewModel (Repository repository) {
        this.repository = repository;
    }
}
