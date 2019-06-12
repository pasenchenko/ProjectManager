package space.flogiston.projectmanager.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "errors")
public class Err {
    @PrimaryKey
    public Integer id;

    public String errorMessage;

    public Long timestamp;
}
