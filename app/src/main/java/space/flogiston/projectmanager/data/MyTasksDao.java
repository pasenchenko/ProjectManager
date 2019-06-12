package space.flogiston.projectmanager.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.MyTasks;
import space.flogiston.projectmanager.data.entities.MyTasksList;
import space.flogiston.projectmanager.data.entities.ProjectsList;

@Dao
public interface MyTasksDao {

    @Insert
    void insertMyTasks (List<MyTasksList> myTasksList);

    @Query("SELECT * FROM `mytasks` WHERE `projectId` = :projectId")

    List<MyTasksList> getMyTasks(int projectId);

    @Query("DELETE FROM `mytasks` WHERE `projectId` = :projectId")

    void deleteMyTasks (int projectId);

    @Query("DELETE FROM `mytasks`")

    void deleteAll ();

}
