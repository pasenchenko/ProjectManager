package space.flogiston.projectmanager.data.entities;

public class CreateProject {
    private String action;

    private Integer id;

    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setAction(String action){
        this.action = action;
    }
    public String getAction() {
        return this.action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
