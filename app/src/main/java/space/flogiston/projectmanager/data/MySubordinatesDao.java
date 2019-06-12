package space.flogiston.projectmanager.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.SubordinatesList;

@Dao
public interface MySubordinatesDao {

    @Insert
    void insertMySubordinates (List<SubordinatesList> subordinatesList);

    @Query("SELECT * FROM `subordinates` WHERE `projectId` = :projectId")

    List<SubordinatesList> getMySubordinates(int projectId);

    @Query("DELETE FROM `subordinates` WHERE `projectId` = :projectId")

    void deleteSubordinates (int projectId);

    @Query("DELETE FROM `subordinates`")

    void deleteAll ();

}
