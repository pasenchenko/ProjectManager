package space.flogiston.projectmanager.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import space.flogiston.projectmanager.viewmodels.LoginViewModel;
import space.flogiston.projectmanager.ModelFactory;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements Observer<Login> {
    private SharedPreferences sPref;
    private EditText emailField;
    private EditText passwordField;
    private TextView loginState;
    private MutableLiveData<Login> loginData;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = getSharedPreferences("project_manager", MODE_PRIVATE);
        String apiToken = sPref.getString("api_token", "");
        if (apiToken.length() > 0) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MyProjectsActivity.class);
            startActivity(intent);
            return;
        }
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setTitle(getString(R.string.sign_in));
        } catch (Exception e) {

        }
        Repository repository = ((ProjectManager)getApplication()).getRepository();
        loginViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(LoginViewModel.class);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        loginState = findViewById(R.id.login_state);
    }
    @Override
    public void onChanged(Login loginData) {
        if (loginData != null) {
            if (loginData.getError() == null) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("api_token", loginData.getToken());
                ed.apply();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MyProjectsActivity.class);
                startActivity(intent);
            } else if (loginData.getError().equals("wrong")) {
                loginState.setText(getString(R.string.wrong_email_password));
            }
        }
    }
    public void login (View view) {
        loginState.setText("");
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        loginViewModel.login(email, password);
        loginData = loginViewModel.getLoginData();
        loginData.observe(this, this);
    }
}
