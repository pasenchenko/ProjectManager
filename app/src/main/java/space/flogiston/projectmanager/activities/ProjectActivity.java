package space.flogiston.projectmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import space.flogiston.projectmanager.ModelFactory;
import space.flogiston.projectmanager.adapters.ProjectFragmentPagerAdapter;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.viewmodels.ProjectViewModel;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.ProjectsList;

import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProjectActivity extends AppCompatActivity {

    private ProjectViewModel projectViewModel;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ProjectsList project;
    private SharedPreferences sPref;
    private String apiToken;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        repository = ((ProjectManager)getApplication()).getRepository();
        /*projectViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(ProjectViewModel.class);*/
        project = ((ProjectManager)getApplication()).getCurrentProject();

        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setTitle(project.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }

        sPref = getSharedPreferences("project_manager", MODE_PRIVATE);
        apiToken = sPref.getString("api_token", "");

        //repository.getMySubordinates(project.getId(), apiToken);
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ProjectFragmentPagerAdapter(this, getSupportFragmentManager(), project);
        pager.setAdapter(pagerAdapter);
    }
    public ProjectsList getProject () {
        return this.project;
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
