package space.flogiston.projectmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.entities.Err;
import space.flogiston.projectmanager.data.entities.ProjectsList;

public class ErrorsListAdapter extends RecyclerView.Adapter<ErrorsListAdapter.ErrorsListHolder>{
    private List<Err> data;
    private Context context;

    public ErrorsListAdapter(List<Err> errorsList, Context context) {

        this.data = errorsList;
        this.context = context;
        notifyDataSetChanged();
    }

    public void changeData(List<Err> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    class ErrorsListHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView errorMessage;

        ErrorsListHolder(View view) {
            super(view);
            date = view.findViewById(R.id.error_date);
            errorMessage = view.findViewById(R.id.error_message);
        }

        void setContent(String dateStr, String errorMessageStr) {
            date.setText(dateStr);
            errorMessage.setText(errorMessageStr);
        }
    }

    @NonNull
    @Override
    public ErrorsListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.error_item, viewGroup, false);
        return new ErrorsListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ErrorsListHolder errorsListHolder, final int i) {
        long timestamp = data.get(i).timestamp;
        String errorMessage = data.get(i).errorMessage;
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);

        DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        String dateString = df.format(calendar.getTime());


        errorsListHolder.setContent(dateString, errorMessage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

