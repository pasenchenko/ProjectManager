package space.flogiston.projectmanager.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.Login;

public class LoginViewModel extends ViewModel {
    private Repository repository;
    MutableLiveData<Login> loginData;
    public LoginViewModel (Repository repository) {
        this.repository = repository;
    }
    public void login (String email, String password) {
        loginData = repository.login(email, password);
    }
    public MutableLiveData<Login> getLoginData () {
        return loginData;
    }
}
