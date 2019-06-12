package space.flogiston.projectmanager.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.ModelFactory;
import space.flogiston.projectmanager.adapters.MyProjectsAdapter;
import space.flogiston.projectmanager.viewmodels.MyProjectsViewModel;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.CreateProject;
import space.flogiston.projectmanager.data.entities.MyInfo;
import space.flogiston.projectmanager.data.entities.MyProjects;
import space.flogiston.projectmanager.data.entities.ProjectsList;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MyProjectsActivity extends AppCompatActivity implements
        Observer<Object> {
    private RecyclerView myProjectsList;
    private TextView navHeaderMyName;
    private NavigationView navigationView;
    private MyProjectsAdapter myProjectsAdapter;
    private SharedPreferences sPref;
    private AlertDialog createProjectDialog;
    private MyProjectsViewModel myProjectsViewModel;
    private String apiToken;
    private MutableLiveData<MyInfo> myInfoLiveData;
    private MutableLiveData<MyProjects> myProjectsLiveData;
    private MutableLiveData<CreateProject> createProjectLiveData;
    private EditText cpdProjectName;
    private EditText cpdProjectDescription;
    private EditText cpdMyRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprojects);
        Repository repository = ((ProjectManager)getApplication()).getRepository();
        myProjectsViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(MyProjectsViewModel.class);

        sPref = getSharedPreferences("project_manager", MODE_PRIVATE);
        apiToken = sPref.getString("api_token", "");

        myProjectsList = findViewById(R.id.myprojects_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL,
                false);
        myProjectsList.setLayoutManager(layoutManager);
        myProjectsAdapter = new MyProjectsAdapter(new ArrayList<ProjectsList>(),
                MyProjectsActivity.this);
        myProjectsList.setAdapter(myProjectsAdapter);


        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.create_new_project);

        LayoutInflater inflater = getLayoutInflater();

        adb.setView(inflater.inflate(R.layout.project_dialog, null));
        createProjectDialog = adb.create();
        //cpdProjectName = createProjectDialog.findViewById(R.id.CPD_project_name);
        //cpdProjectDescription = createProjectDialog.findViewById(R.id.CPD_project_description);
        //cpdMyRole = createProjectDialog.findViewById(R.id.CPD_my_role);


        myProjectsViewModel.requestMyInfo(apiToken);
        myInfoLiveData = myProjectsViewModel.getMyInfo();
        myInfoLiveData.observe(this, this);


        myProjectsViewModel.requestMyProjects(apiToken);

        myProjectsLiveData = myProjectsViewModel.getMyProjects();
        myProjectsLiveData.observe(this, this);


    }

    @Override
    public void onChanged(Object object) {
        if (object != null) {
            try {
                MyInfo myInfo = (MyInfo) object;
                /*
                if (myInfo.getError() == null) {

                }*/
            } catch (ClassCastException cce) {
                try {
                    MyProjects myProjects = (MyProjects) object;
                    if (myProjects.getError() == null) {
                        Log.e("asd", "" + myProjects.getProjectsList().size());
                        myProjectsAdapter.changeData(myProjects.getProjectsList());
                    } else if (myProjects.getProjectsList() == null) {
                        Log.e("asd", "NUUULL!!!");
                    } else {
                        Log.e("asd", myProjects.getError());
                    }
                } catch (ClassCastException cce1) {
                    CreateProject createProject = (CreateProject) object;
                    if (createProject.getError() == null) {
                        myProjectsLiveData.removeObserver(this);
                        myProjectsViewModel.requestMyProjects(apiToken);
                        myProjectsLiveData = myProjectsViewModel.getMyProjects();
                        myProjectsLiveData.observe(this, this);
                        createProjectDialog.hide();
                    }
                }
            }
        }
    }
    public void onCreateProjectClick (View view) {
        createProjectDialog.show();
        ((EditText)createProjectDialog.findViewById(R.id.CPD_project_name)).setText("");
        ((EditText)createProjectDialog.findViewById(R.id.CPD_project_description)).setText("");
        ((EditText)createProjectDialog.findViewById(R.id.CPD_my_role)).setText("");
    }
    public void onCreateProjectSubmitClick (View view) {
        String name = ((EditText)createProjectDialog.findViewById(R.id.CPD_project_name))
                .getText().toString();
        String description = ((EditText)createProjectDialog.findViewById(R.id.CPD_project_description))
                .getText().toString();
        String myRole = ((EditText)createProjectDialog.findViewById(R.id.CPD_my_role))
                .getText().toString();
        myProjectsViewModel.requestCreateProject(name, description, myRole, apiToken);
        createProjectLiveData = myProjectsViewModel.createProject();
        createProjectLiveData.observe(this, this);
        //Toast.makeText(this, name + "/" + description + "/" + myRole, Toast.LENGTH_SHORT).show();
        createProjectDialog.hide();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_log) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ErrorActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_exit) {
            myProjectsViewModel.exit();
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("api_token", "");
            ed.apply();
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}