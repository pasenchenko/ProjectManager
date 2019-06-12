package space.flogiston.projectmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.Logger;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.entities.MyTasksList;

public class MyTasksListAdapter
        extends RecyclerView.Adapter<MyTasksListAdapter.MyTasksListHolder>{
    private ArrayList<MyTasksList> data;
    private Context context;

    public MyTasksListAdapter(ArrayList<MyTasksList> myTasks, Context context) {
        this.data = myTasks;
        this.context = context;
        notifyDataSetChanged();
    }

    public void changeData(ArrayList<MyTasksList> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    class MyTasksListHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private TextView name;
        private TextView points;
        private TextView priority;


        MyTasksListHolder(View view) {
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
                priority.setText(context.getString(R.string.low));
            } else if (taskPriority == 2) {
                priority.setText(context.getString(R.string.medium));
            } else {
                priority.setText(context.getString(R.string.high));
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
    public MyTasksListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.task_item, viewGroup, false);
        return new MyTasksListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTasksListHolder MyTasksListHolder, final int i) {
        String name = data.get(i).getName();
        int points = data.get(i).getPoints();
        int priority = data.get(i).getPriority();
        int status = data.get(i).getStatus();
        MyTasksListHolder.setContent(name, points, priority, status);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}



