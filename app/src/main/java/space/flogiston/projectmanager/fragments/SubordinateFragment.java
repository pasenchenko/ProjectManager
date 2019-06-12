package space.flogiston.projectmanager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.Logger;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.adapters.MyTasksListAdapter;
import space.flogiston.projectmanager.adapters.SubordinatesTasksListAdapter;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.MyTasks;
import space.flogiston.projectmanager.data.entities.MyTasksList;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.data.entities.SubordinatesList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasks;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

import static android.content.Context.MODE_PRIVATE;

public class SubordinateFragment extends Fragment implements Observer<MyTasks> {
    private TextView myRole;
    private TextView myBoss;
    private RecyclerView myTasksList;
    private SharedPreferences sPref;
    private String apiToken;
    private MutableLiveData<MyTasks> myTasks;
    private MyTasksListAdapter myTasksListAdapter;
    private Repository repository;
    private ProjectsList project;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subordinate, container, false);

        myRole = view.findViewById(R.id.project_myRole);
        myBoss = view.findViewById(R.id.project_myBoss);
        myTasksList = view.findViewById(R.id.mytasks_list);
        repository = ((ProjectManager) getActivity().getApplication()).getRepository();
        project = ((ProjectActivity) getActivity()).getProject();

        myRole.setText(getString(R.string.my_role) + ": " + project.getRole());
        myBoss.setText(getString(R.string.my_boss) + ": " + project.getFirstName()
                + " " + project.getLastName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),
                RecyclerView.VERTICAL,
                false);
        myTasksList.setLayoutManager(layoutManager);
        myTasksListAdapter = new MyTasksListAdapter(
                new ArrayList<MyTasksList>(),
                view.getContext());
        myTasksList.setAdapter(myTasksListAdapter);


        sPref = getActivity().getSharedPreferences("project_manager", MODE_PRIVATE);
        apiToken = sPref.getString("api_token", "");
        myTasks = repository.getMyTasks(project.getId(), apiToken);
        myTasks.observe(this, this);
        return view;
    }

    public void onChanged(MyTasks myTasks) {
        if (myTasks.getError() == null) {
            myTasksListAdapter.changeData(myTasks.getMyTasksList());
        }
    }
}