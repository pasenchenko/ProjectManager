package space.flogiston.projectmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.data.entities.SubordinatesList;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

public class SubordinatesTasksListAdapter
        extends RecyclerView.Adapter<SubordinatesTasksListAdapter.SubordinatesTasksListHolder>{
    private ArrayList<SubordinatesTasksList> data;
    private Context context;

    public SubordinatesTasksListAdapter(ArrayList<SubordinatesTasksList> subordinatesTasks, Context context) {
        this.data = subordinatesTasks;
        this.context = context;
        notifyDataSetChanged();
    }

    public void changeData(ArrayList<SubordinatesTasksList> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    class SubordinatesTasksListHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private TextView name;
        private TextView points;
        private TextView priority;


        SubordinatesTasksListHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.taskLayout);
            name = view.findViewById(R.id.taskName);
            points = view.findViewById(R.id.taskPoints);
            priority = view.findViewById(R.id.taskPriority);
        }

        void setContent(String taskName, int taskPoints, int taskPriority, int taskStatus) {
            name.setText(taskName);
            points.setText("" + taskPoints);
            if (taskPriority == 1) {
                priority.setText("Низкий");
            } else if (taskPriority == 2) {
                priority.setText("Средний");
            } else {
                priority.setText("Высокий");
            }

            if (taskStatus == 0) {
                layout.setBackgroundColor(ContextCompat.getColor(context, R.color.unFinishedTaskColor));
            } else {
                layout.setBackgroundColor(ContextCompat.getColor(context, R.color.finishedTaskColor));
            }
        }
    }

    @NonNull
    @Override
    public SubordinatesTasksListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.task_item, viewGroup, false);
        return new SubordinatesTasksListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubordinatesTasksListHolder SubordinatesListHolder, final int i) {
        String name = data.get(i).getName();
        int points = data.get(i).getPoints();
        int priority = data.get(i).getPriority();
        int status = data.get(i).getStatus();
        SubordinatesListHolder.setContent(name, points, priority, status);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}


