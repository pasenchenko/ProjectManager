package space.flogiston.projectmanager.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.ProjectsList;

@Dao
public interface MyColleaguesDao {

    @Insert
    void insertMyColleagues (List<ColleaguesList> colleaguesList);

    @Query("SELECT * FROM `colleagues` WHERE `projectId` = :projectId")

    List<ColleaguesList> getMyColleagues(int projectId);

    @Query("DELETE FROM `colleagues` WHERE `projectId` = :projectId")

    void deleteColleagues (int projectId);

    @Query("DELETE FROM `colleagues`")

    void deleteAll ();

}
