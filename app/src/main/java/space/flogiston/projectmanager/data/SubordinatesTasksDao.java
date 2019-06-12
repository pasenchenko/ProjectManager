package space.flogiston.projectmanager.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.projectmanager.data.entities.MyTasksList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

@Dao
public interface SubordinatesTasksDao {
    @Insert
    void insertSubordinatesTasks (List<SubordinatesTasksList> subordinatesTasksList);

    @Query("SELECT * FROM `subordinatestasks` WHERE `projectId` = :projectId")

    List<SubordinatesTasksList> getSubordinatesTasks(int projectId);

    @Query("DELETE FROM `subordinatestasks` WHERE `projectId` = :projectId")

    void deleteSubordinatesTasks (int projectId);

    @Query("DELETE FROM `subordinatestasks`")

    void deleteAll ();

}
