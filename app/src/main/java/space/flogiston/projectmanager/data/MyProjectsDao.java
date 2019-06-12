package space.flogiston.projectmanager.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.projectmanager.data.entities.ProjectsList;

@Dao
public interface MyProjectsDao {

    @Insert
    void insertMyProjects (List<ProjectsList> projectsList);

    @Query("SELECT * FROM `projects`")

    List<ProjectsList> getMyProjects();

    @Query("DELETE FROM `projects`")

    void deleteAll ();

}
