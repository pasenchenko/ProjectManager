package space.flogiston.projectmanager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.ModelFactory;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.activities.MyProjectsActivity;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.adapters.ColleaguesListAdapter;
import space.flogiston.projectmanager.adapters.MyProjectsAdapter;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.CreateProject;
import space.flogiston.projectmanager.data.entities.MyColleagues;
import space.flogiston.projectmanager.data.entities.MyInfo;
import space.flogiston.projectmanager.data.entities.MyProjects;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.viewmodels.ColleaguesListViewModel;
import space.flogiston.projectmanager.viewmodels.MyProjectsViewModel;

import static android.content.Context.MODE_PRIVATE;

public class ColleaguesListFragment extends Fragment implements Observer<MyColleagues> {
    private ColleaguesListViewModel colleaguesListViewModel;
    private SharedPreferences sPref;
    private String apiToken;
    private ProjectsList project;
    private MutableLiveData<MyColleagues> myColleagues;
    private RecyclerView colleaguesList;
    private ColleaguesListAdapter colleaguesListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_colleagues, container, false);
        Repository repository = ((ProjectManager) getActivity().getApplication()).getRepository();
        /*colleaguesListViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(ColleaguesListViewModel.class);*/

        colleaguesList = view.findViewById(R.id.mycolleagues_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),
                RecyclerView.VERTICAL,
                false);
        colleaguesList.setLayoutManager(layoutManager);
        colleaguesListAdapter = new ColleaguesListAdapter(new ArrayList<ColleaguesList>(),
                view.getContext());
        colleaguesList.setAdapter(colleaguesListAdapter);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        sPref = getActivity().getSharedPreferences("project_manager", MODE_PRIVATE);
        apiToken = sPref.getString("api_token", "");

        this.project = ((ProjectActivity) getActivity()).getProject();

        myColleagues = repository.getMyColleagues(project.getId(), apiToken);
        myColleagues.observe(this, this);
        /*colleaguesListViewModel.requestMyColleagues(project.getId(), apiToken);
        myColleagues = colleaguesListViewModel.getMyColleagues();
        myColleagues.observe(this, this);*/


        Log.e("asd", "colleagues launched");
        return view; //super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onChanged(MyColleagues myColleagues) {
        // Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
        if (myColleagues != null) {
            if (myColleagues.getError() == null) {
                Log.e("asd", "colleagues noerrors" + myColleagues.getColleaguesList().size());

                colleaguesListAdapter.changeData(myColleagues.getColleaguesList());
            } else if (myColleagues.getColleaguesList() == null) {
                Log.e("asd", "NUUULL!!!");
            } else {
                Log.e("asd", myColleagues.getError());
            }
        } else {
            Log.e("asd", "colleagues received null");
        }
    }
}
