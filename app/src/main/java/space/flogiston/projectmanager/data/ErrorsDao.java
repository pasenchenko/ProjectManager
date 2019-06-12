package space.flogiston.projectmanager.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.projectmanager.data.entities.Err;
import space.flogiston.projectmanager.data.entities.ProjectsList;

@Dao
public interface ErrorsDao {

    @Insert
    void insertError (Err err);

    @Query("SELECT * FROM `errors` ORDER BY `timestamp` DESC")

    List<Err> getErrors();

    @Query("DELETE FROM `errors`")

    void deleteAll ();

}
