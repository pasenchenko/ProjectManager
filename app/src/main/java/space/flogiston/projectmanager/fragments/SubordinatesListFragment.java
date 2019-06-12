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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.adapters.SubordinatesListAdapter;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.SubordinatesList;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.ProjectsList;

import static android.content.Context.MODE_PRIVATE;

public class SubordinatesListFragment extends Fragment implements Observer<MySubordinates> {
    private SharedPreferences sPref;
    private String apiToken;
    private ProjectsList project;
    private MutableLiveData<MySubordinates> mySubordinates;
    private RecyclerView subordinatesList;
    private SubordinatesListAdapter subordinatesListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_subordinates, container, false);
        Repository repository = ((ProjectManager) getActivity().getApplication()).getRepository();
        /*SubordinatesListViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(SubordinatesListViewModel.class);*/

        subordinatesList = view.findViewById(R.id.mysubordinates_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),
                RecyclerView.VERTICAL,
                false);
        subordinatesList.setLayoutManager(layoutManager);
        subordinatesListAdapter = new SubordinatesListAdapter(new ArrayList<SubordinatesList>(),
                view.getContext());
        subordinatesList.setAdapter(subordinatesListAdapter);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        sPref = getActivity().getSharedPreferences("project_manager", MODE_PRIVATE);
        apiToken = sPref.getString("api_token", "");

        this.project = ((ProjectActivity) getActivity()).getProject();

        mySubordinates = repository.getMySubordinates(project.getId(), apiToken);
        mySubordinates.observe(this, this);
        /*SubordinatesListViewModel.requestMySubordinates(project.getId(), apiToken);
        mySubordinates = SubordinatesListViewModel.getMySubordinates();
        mySubordinates.observe(this, this);*/

        return view;
    }
    @Override
    public void onChanged(MySubordinates mySubordinates) {
        //Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
        if (mySubordinates != null) {
            if (mySubordinates.getError() == null) {
                Log.e("asd", "Subordinates noerrors" + mySubordinates.getSubordinatesList().size());

                subordinatesListAdapter.changeData(mySubordinates.getSubordinatesList());
            } else if (mySubordinates.getSubordinatesList() == null) {
                Log.e("asd", "NUUULL!!!");
            } else {
                Log.e("asd", mySubordinates.getError());
            }
        } else {
            Log.e("asd", "Subordinates received null");
        }
    }
}
