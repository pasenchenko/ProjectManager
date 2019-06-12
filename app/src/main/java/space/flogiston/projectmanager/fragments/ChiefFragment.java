package space.flogiston.projectmanager.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.activities.GanttChartActivity;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.adapters.SubordinatesTasksListAdapter;
import space.flogiston.projectmanager.data.Repository;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.data.entities.SubordinatesList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasks;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

import static android.content.Context.MODE_PRIVATE;

public class ChiefFragment extends Fragment implements Observer<Object> {
    private ProjectsList project;
    private AlertDialog addUserDialog;
    private AlertDialog changeProjectDialog;
    private AlertDialog deleteProjectDialog;
    private AlertDialog addTaskDialog;
    private Button addUserBtn;
    private Button addTaskBtn;
    private Button changeProjectBtn;
    private Button deleteProjectBtn;
    private Button ganttChartBtn;
    private RecyclerView subordinatesTasksList;
    private SharedPreferences sPref;
    private String apiToken;
    private MutableLiveData<SubordinatesTasks> subordinatesTasks;
    private MutableLiveData<MySubordinates> mySubordinates;
    private SubordinatesTasksListAdapter subordinatesTasksListAdapter;
    private ArrayList<String> subordinates;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chief, container, false);
        this.project = ((ProjectActivity) getActivity()).getProject();
        final int projectId = this.project.getId();
        subordinates = new ArrayList<>();
        final Repository repository = ((ProjectManager) getActivity().getApplication()).getRepository();
        // Buttons
        addUserBtn = view.findViewById(R.id.addUserBtn);
        addTaskBtn = view.findViewById(R.id.addTaskBtn);
        if (project.getSubordinated() != 1) {
            changeProjectBtn = view.findViewById(R.id.changeProjectBtn);
            deleteProjectBtn = view.findViewById(R.id.deleteProjectBtn);
        } else {
            view.findViewById(R.id.changeProjectBtn).setVisibility(View.GONE);
            view.findViewById(R.id.deleteProjectBtn).setVisibility(View.GONE);
        }
        ganttChartBtn = view.findViewById(R.id.showGanttBtn);
        addButtonsHandlers();

        ((TextView) view.findViewById(R.id.myRoleView)).setText(
                getActivity().getString(R.string.my_role) + ": " + project.getRole());
        subordinatesTasksList = view.findViewById(R.id.subordinatestasks_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),
                RecyclerView.VERTICAL,
                false);
        subordinatesTasksList.setLayoutManager(layoutManager);
        subordinatesTasksListAdapter = new SubordinatesTasksListAdapter(
                new ArrayList<SubordinatesTasksList>(),
                view.getContext());
        subordinatesTasksList.setAdapter(subordinatesTasksListAdapter);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        createDialogs();


        sPref = getActivity().getSharedPreferences("project_manager", MODE_PRIVATE);
        apiToken = sPref.getString("api_token", "");

        mySubordinates = repository.getMySubordinates(project.getId(), apiToken);
        mySubordinates.observe(this, this);
        subordinatesTasks = repository.getMySubordinatesTasks(project.getId(), apiToken);
        subordinatesTasks.observe(this, this);

        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onChanged(Object object) {
        // Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
        try {
            SubordinatesTasks subordinatesTasks = (SubordinatesTasks) object;
            if (subordinatesTasks.getError() == null) {
                subordinatesTasksListAdapter.changeData(subordinatesTasks.getTasksList());
            }
        } catch (ClassCastException cce) {
            MySubordinates mySubordinates = (MySubordinates) object;
            if (mySubordinates.getError() == null) {
                ArrayList<SubordinatesList> subordinatesLists = mySubordinates.getSubordinatesList();
                for (SubordinatesList s : subordinatesLists) {
                    subordinates.add(s.getFirstName() + " " + s.getLastName());
                }
            }
        }
    }

    public void addButtonsHandlers() {
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserDialog.show();
                addUserDialog.findViewById(R.id.AUD_submit)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onAddUserSubmit(v);
                            }
                        });
            }
        });
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog.show();

                Button startDateBtn = addTaskDialog.findViewById(R.id.ATD_start_date_pick);
                Button finishDateBtn = addTaskDialog.findViewById(R.id.ATD_finish_date_pick);
                Button addTaskSubmit = addTaskDialog.findViewById(R.id.ATD_submit_button);
                startDateBtn.setText(getContext().getString(R.string.pick));
                finishDateBtn.setText(getContext().getString(R.string.pick));
                addTaskSubmit.setText(getString(R.string.create));

                //String[] arr = new String[20];
                //ArrayAdapter<String> adapter =
                //        new ArrayAdapter<String>(this, subordinates.toArray(arr));
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //Spinner spinner = (Spinner) addTaskDialog.findViewById(R.id.spinner);
                //spinner.setAdapter(adapter);


                addTaskDialog.findViewById(R.id.ATD_start_date_pick)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                addTaskDialog.findViewById(R.id.ATD_finish_date_pick)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
            }
        });
        if (project.getSubordinated() != 1) {
            changeProjectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeProjectDialog.show();
                    ((Button)changeProjectDialog.findViewById(R.id.create_project_submit))
                            .setText(getString(R.string.change));

                    ((EditText) changeProjectDialog.findViewById(R.id.CPD_project_name))
                            .setText(project.getName());
                    ((EditText) changeProjectDialog.findViewById(R.id.CPD_project_description))
                            .setText(project.getDescription());
                    ((EditText) changeProjectDialog.findViewById(R.id.CPD_my_role))
                            .setText(project.getRole());
                }
            });
            deleteProjectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProjectDialog.show();
                }
            });
        }
        ganttChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), GanttChartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onAddUserSubmit(View view) {

    }

    public void onChangeProjectSubmit(View view) {

    }

    public void onDeleteProjectSubmit(View view) {

    }

    public void onAddTaskSumbit(View view) {

    }

    public void createDialogs() {
        LayoutInflater inflater = getLayoutInflater();
        // AddUserDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this.getContext());
        adb.setTitle(R.string.add_user);
        adb.setView(inflater.inflate(R.layout.add_user_dialog, null));
        addUserDialog = adb.create();


        // ChangeProjectDialog
        adb = new AlertDialog.Builder(getContext());
        adb.setTitle(R.string.change_project);
        adb.setView(inflater.inflate(R.layout.project_dialog, null));
        changeProjectDialog = adb.create();
        // DeleteProjectDialog
        adb = new AlertDialog.Builder(getContext());
        adb.setMessage(getContext().getString(R.string.sure_project_delete) + ": " + project.getName())
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteProjectDialog.hide();
                    }
                });
        // Create the AlertDialog object and return it
        deleteProjectDialog = adb.create();

        //adb = new AlertDialog.Builder(getContext());
        //adb.setTitle(R.string.delete_project);
        //adb.setView(inflater.inflate(R.layout.delete_project_dialog, null));
        //deleteProjectDialog = adb.create();

        // AddTaskDialog
        adb = new AlertDialog.Builder(getContext());
        adb.setTitle(R.string.new_task);
        adb.setView(inflater.inflate(R.layout.task_dialog, null));
        addTaskDialog = adb.create();
    }
}
