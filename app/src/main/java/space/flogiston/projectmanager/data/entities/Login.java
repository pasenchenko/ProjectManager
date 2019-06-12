package space.flogiston.projectmanager.data.entities;

public class Login {
    private String action;

    private String token;

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
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return this.token;
    }
}

