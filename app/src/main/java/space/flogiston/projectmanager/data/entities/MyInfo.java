package space.flogiston.projectmanager.data.entities;

public class MyInfo {
    private String action;

    private int id;

    private String firstName;

    private String lastName;

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
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return this.lastName;
    }
}
