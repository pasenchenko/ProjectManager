package space.flogiston.projectmanager.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.Err;
import space.flogiston.projectmanager.data.entities.MyTasksList;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.data.entities.SubordinatesList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

@Database(entities = {ProjectsList.class, ColleaguesList.class, SubordinatesList.class,
        MyTasksList.class, SubordinatesTasksList.class, Err.class}, version = 1,
        exportSchema = false)
public abstract class ProjectManagerDB extends RoomDatabase {
    public abstract MyProjectsDao myProjectsDao();
    public abstract MyColleaguesDao myColleaguesDao();
    public abstract MySubordinatesDao mySubordinatesDao();
    public abstract MyTasksDao myTasksDao();
    public abstract SubordinatesTasksDao subordinatesTasksDao();
    public abstract ErrorsDao errorsDao();
}
