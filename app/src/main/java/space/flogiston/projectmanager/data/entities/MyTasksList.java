package space.flogiston.projectmanager.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mytasks")
public class MyTasksList
{
    @PrimaryKey
    private int id;

    private String name;

    private String description;

    private int points;

    private int priority;

    private int status;

    private int projectId;

    private long startDate;

    private long finishDate;

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(long finishDate) {
        this.finishDate = finishDate;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setPoints(int points){
        this.points = points;
    }
    public int getPoints(){
        return this.points;
    }
    public void setPriority(int priority){
        this.priority = priority;
    }
    public int getPriority(){
        return this.priority;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setProjectId(int projectId){
        this.projectId = projectId;
    }
    public int getProjectId(){
        return this.projectId;
    }
}

