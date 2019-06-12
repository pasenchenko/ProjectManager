package space.flogiston.projectmanager.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import space.flogiston.projectmanager.data.entities.CreateProject;
import space.flogiston.projectmanager.data.entities.Login;
import space.flogiston.projectmanager.data.entities.MyColleagues;
import space.flogiston.projectmanager.data.entities.MyInfo;
import space.flogiston.projectmanager.data.entities.MyProjects;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.MyTasks;
import space.flogiston.projectmanager.data.entities.SubordinatesTasks;

public interface WebService {
    @GET("/server.php")
    Call<Login> login(@Query("action") String action, @Query("email") String email,
                      @Query("password") String password);
    @GET("/server.php")
    Call<MyInfo> getMyInfo(@Query("action") String action, @Query("authToken")String authToken);

    @GET("/server.php")
    Call<MyProjects> getMyProjects(@Query("action")String action,
                                   @Query("authToken")String authToken);
    @GET("/server.php")
    Call<CreateProject> createProject(@Query("action")String action, @Query("name")String name,
                                      @Query("description")String description,
                                      @Query("myRole")String myRole,
                                      @Query("authToken")String authToken);
    @GET("/server.php")
    Call<MyColleagues> getMyColleagues(@Query("action")String action,
                                     @Query("projectId")int projectId,
                                     @Query("authToken")String authToken);
    @GET("/server.php")
    Call<MySubordinates> getMySubordinates(@Query("action")String action,
                                       @Query("projectId")int projectId,
                                       @Query("authToken")String authToken);
    @GET("/server.php")
    Call<MyTasks> getMyTasks(@Query("action")String action,
                             @Query("projectId")int projectId,
                             @Query("authToken")String authToken);
    @GET("/server.php")
    Call<SubordinatesTasks> getMySubordinatesTasks(@Query("action")String action,
                                                   @Query("projectId")int projectId,
                                                   @Query("authToken")String authToken);
}
