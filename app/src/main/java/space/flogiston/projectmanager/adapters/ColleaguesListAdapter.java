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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.activities.ProjectActivity;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.ProjectsList;

public class ColleaguesListAdapter extends RecyclerView.Adapter<ColleaguesListAdapter.ColleaguesListHolder>{
    private ArrayList<ColleaguesList> data;
    private Context context;

    public ColleaguesListAdapter(ArrayList<ColleaguesList> myColleagues, Context context) {
        this.data = myColleagues;
        this.context = context;
        notifyDataSetChanged();
    }

    public void changeData(ArrayList<ColleaguesList> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    class ColleaguesListHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatar;
        private TextView name;
        private TextView role;
        private TextView rating;

        ColleaguesListHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.userAvatar);
            name = view.findViewById(R.id.userName);
            role = view.findViewById(R.id.userRole);
            rating = view.findViewById(R.id.userRating);
        }

        void setContent(String userAvatar, String userName, String userRole, String userRating) {
            byte[] decodedString = Base64.decode(userAvatar, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            avatar.setImageBitmap(decodedByte);
            name.setText(userName);
            role.setText(userRole);
            rating.setText(userRating);
        }
    }

    @NonNull
    @Override
    public ColleaguesListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.user_item, viewGroup, false);
        return new ColleaguesListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColleaguesListHolder colleaguesListHolder, final int i) {
        String avatar = data.get(i).getAvatar();
        String userName = data.get(i).getFirstName() + " " + data.get(i).getLastName();
        String userRole = data.get(i).getRole();
        String userRating = "" + data.get(i).getRating();
        colleaguesListHolder.setContent(avatar, userName, userRole, userRating);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
