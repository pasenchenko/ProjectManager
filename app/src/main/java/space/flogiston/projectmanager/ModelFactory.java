package space.flogiston.projectmanager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.viewmodels.ColleaguesListViewModel;
import space.flogiston.projectmanager.viewmodels.LoginViewModel;
import space.flogiston.projectmanager.viewmodels.MyProjectsViewModel;
import space.flogiston.projectmanager.viewmodels.ProjectViewModel;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    public ModelFactory(Repository repository) {
        super();
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LoginViewModel.class) {
            return (T) new LoginViewModel(repository);
        } else if (modelClass == MyProjectsViewModel.class) {
            return (T) new MyProjectsViewModel(repository);
        } else if (modelClass == ColleaguesListViewModel.class) {
            return (T) new ColleaguesListViewModel(repository);
        }
        return null;
    }
}

