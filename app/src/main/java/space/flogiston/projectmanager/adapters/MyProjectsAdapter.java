package space.flogiston.projectmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.entities.ProjectsList;

public class MyProjectsAdapter extends RecyclerView.Adapter<MyProjectsAdapter.MyProjectsHolder>{
    private List<ProjectsList> data;
    private Context context;

    public MyProjectsAdapter(List<ProjectsList> myProjects, Context context) {
        this.data = myProjects;
        this.context = context;
        notifyDataSetChanged();
    }

    public void changeData(List<ProjectsList> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    class MyProjectsHolder extends RecyclerView.ViewHolder {
        private TextView projectName;
        private TextView myRole;

        MyProjectsHolder(View view) {
            super(view);
            projectName = view.findViewById(R.id.project_name);
            myRole = view.findViewById(R.id.my_role);
        }

        void setContent(String projectNameValue, String myRoleValue) {
            projectName.setText(projectNameValue);
            myRole.setText(myRoleValue);
        }
    }

    @NonNull
    @Override
    public MyProjectsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.project_item, viewGroup, false);
        return new MyProjectsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProjectsHolder myProjectsHolder, final int i) {
        String projectName = data.get(i).getName();
        String myRole = data.get(i).getRole();
        myProjectsHolder.setContent(projectName, myRole);
        myProjectsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProjectManager)context.getApplicationContext()).setCurrentProject(data.get(i));
                Intent intent = new Intent();
                intent.setClass(context.getApplicationContext(), ProjectActivity.class);
                //intent.putExtra("project", data.get(i));
                context.startActivity(intent);
            }
        });
        myProjectsHolder.setContent(projectName, myRole);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
