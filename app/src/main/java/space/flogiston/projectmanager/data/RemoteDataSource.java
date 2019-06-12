package space.flogiston.projectmanager.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import space.flogiston.projectmanager.Logger;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.entities.CreateProject;
import space.flogiston.projectmanager.data.entities.Login;
import space.flogiston.projectmanager.data.entities.MyColleagues;
import space.flogiston.projectmanager.data.entities.MyInfo;
import space.flogiston.projectmanager.data.entities.MyProjects;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.MyTasks;
import space.flogiston.projectmanager.data.entities.SubordinatesTasks;

public class RemoteDataSource {
    private WebService webService;
    private Context context;
    RemoteDataSource(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://9648637.xyz")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webService = retrofit.create(WebService.class);
    }

    public boolean checkInternetConnection () {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
    Login login (String email, String password) {
        Call<Login> call = webService.login("login", email, password);
        try {
            Response<Login> response = call.execute();
            if (response.isSuccessful()) {
                Login loginResult = response.body();
                return loginResult;
            } else if (response.code() == 400) {
                Login login = new Gson().fromJson(response.errorBody().string(), Login.class);
                return login;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
    MyInfo getMyInfo (String token) {
        Call<MyInfo> call = webService.getMyInfo("getMyInfo", token);
        try {
            Response<MyInfo> response = call.execute();
            if (response.isSuccessful()) {
                MyInfo myInfoResult = response.body();
                return myInfoResult;
            } else if (response.code() == 400) {
                MyInfo myInfo = new Gson().fromJson(response.errorBody().string(), MyInfo.class);
                return myInfo;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
    MyProjects getMyProjects (String token) {
        Call<MyProjects> call = webService.getMyProjects("getMyProjects", token);
        try {
            Response<MyProjects> response = call.execute();
            if (response.isSuccessful()) {
                MyProjects myProjectsResult = response.body();
                return myProjectsResult;
            } else if (response.code() == 400) {
                MyProjects myProjects = new Gson().fromJson(response.errorBody().string(), MyProjects.class);
                return myProjects;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
    CreateProject createProject (String name, String description, String myRole, String token) {
        new Logger(context, "create project");
        Call<CreateProject> call =
                webService.createProject("createProject", name, description, myRole, token);
        try {
            Response<CreateProject> response = call.execute();
            if (response.isSuccessful()) {
                CreateProject createProjectResult = response.body();
                return createProjectResult;
            } else if (response.code() == 400) {
                CreateProject createProject =
                        new Gson().fromJson(response.errorBody().string(), CreateProject.class);
                return createProject;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
    MyColleagues getMyColleagues (int projectId, String token) {
        Call<MyColleagues> call = webService.getMyColleagues("getMyColleagues", projectId, token);
        try {
            Response<MyColleagues> response = call.execute();
            if (response.isSuccessful()) {
                MyColleagues myColleaguesResult = response.body();
                return myColleaguesResult;
            } else if (response.code() == 400) {
                MyColleagues myColleagues =
                        new Gson().fromJson(response.errorBody().string(), MyColleagues.class);
                return myColleagues;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
    MySubordinates getMySubordinates (int projectId, String token) {
        Call<MySubordinates> call =
                webService.getMySubordinates("getMySubordinates", projectId, token);
        try {
            Response<MySubordinates> response = call.execute();
            if (response.isSuccessful()) {
                MySubordinates mySubordinatesResult = response.body();
                return mySubordinatesResult;
            } else if (response.code() == 400) {
                MySubordinates mySubordinates =
                        new Gson().fromJson(response.errorBody().string(), MySubordinates.class);
                return mySubordinates;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
    MyTasks getMyTasks (int projectId, String token) {
        Call<MyTasks> call = webService.getMyTasks("getMyTasks", projectId, token);
        try {
            Response<MyTasks> response = call.execute();
            if (response.isSuccessful()) {
                MyTasks myTasksResult = response.body();
                return myTasksResult;
            } else if (response.code() == 400) {
                MyTasks myTasks =
                        new Gson().fromJson(response.errorBody().string(), MyTasks.class);
                return myTasks;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
    SubordinatesTasks getMySubordinatesTasks (int projectId, String token) {
        Call<SubordinatesTasks> call = webService
                .getMySubordinatesTasks("getMySubordinatesTasks", projectId, token);
        try {
            Response<SubordinatesTasks> response = call.execute();
            if (response.isSuccessful()) {
                SubordinatesTasks subordinatesTasksResult = response.body();
                return subordinatesTasksResult;
            } else if (response.code() == 400) {
                new Logger(context, "Error 400");
                SubordinatesTasks subordinatesTasks =
                        new Gson().fromJson(response.errorBody().string(), SubordinatesTasks.class);
                return subordinatesTasks;
            } else {
                new Logger(context, response.errorBody().toString());
            }
        } catch (IOException ioex) {
            new Logger(context, ioex.getMessage());
            return null;
        }
        return null;
    }
}
