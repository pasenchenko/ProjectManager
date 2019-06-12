package space.flogiston.projectmanager.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.adapters.ErrorsListAdapter;
import space.flogiston.projectmanager.adapters.MyProjectsAdapter;
import space.flogiston.projectmanager.adapters.SubordinatesTasksListAdapter;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.Err;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class ErrorActivity extends AppCompatActivity implements Observer<ArrayList<Err>> {

    private Repository repository;
    private MutableLiveData<ArrayList<Err>> errorLog;
    private RecyclerView errorsList;
    private ErrorsListAdapter errorsListAdapter;
    private SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        repository = ((ProjectManager)getApplication()).getRepository();
        sPref = getSharedPreferences("project_manager", MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setTitle(getString(R.string.error_log));
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }
        errorLog = repository.getErrors();
        errorLog.observe(this, this);

        errorsList = findViewById(R.id.errors_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL,
                false);
        errorsList.setLayoutManager(layoutManager);
        errorsListAdapter = new ErrorsListAdapter(new ArrayList<Err>(),
                ErrorActivity.this);
        errorsList.setAdapter(errorsListAdapter);
    }

    public void onChanged (ArrayList<Err> newErrors) {
        errorsListAdapter.changeData(newErrors);
    }
    public void clearErrorLog (View view) {
        repository.deleteErrors();
        errorsListAdapter.changeData(new ArrayList<Err>());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_log) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ErrorActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_exit) {
            repository.exit();
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
