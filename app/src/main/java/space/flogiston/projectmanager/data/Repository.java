package space.flogiston.projectmanager.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;
import space.flogiston.projectmanager.data.entities.ColleaguesList;
import space.flogiston.projectmanager.data.entities.CreateProject;
import space.flogiston.projectmanager.data.entities.Err;
import space.flogiston.projectmanager.data.entities.Login;
import space.flogiston.projectmanager.data.entities.MyColleagues;
import space.flogiston.projectmanager.data.entities.MyInfo;
import space.flogiston.projectmanager.data.entities.MyProjects;
import space.flogiston.projectmanager.data.entities.MySubordinates;
import space.flogiston.projectmanager.data.entities.MyTasks;
import space.flogiston.projectmanager.data.entities.MyTasksList;
import space.flogiston.projectmanager.data.entities.ProjectsList;
import space.flogiston.projectmanager.data.entities.SubordinatesList;
import space.flogiston.projectmanager.data.entities.SubordinatesTasks;
import space.flogiston.projectmanager.data.entities.SubordinatesTasksList;

import static android.content.Context.MODE_PRIVATE;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public LocalDataSource getLocalDataSource() {
        return localDataSource;
    }

    private final SharedPreferences sPref;

    public Repository(Context context) {
        localDataSource = new LocalDataSource(context);
        remoteDataSource = new RemoteDataSource(context);
        sPref = context.getSharedPreferences("project_manager", MODE_PRIVATE);
    }

    public MutableLiveData<Login> login(final String email, final String password) {
        final MutableLiveData<Login> liveData = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Login loginData = remoteDataSource.login(email, password);
                if (loginData != null) {
                    liveData.postValue(loginData);
                }
            }
        });
        return liveData;
    }

    public MutableLiveData<MyInfo> getMyInfo(final String token) {
        final MutableLiveData<MyInfo> myInfoLiveData = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                MyInfo myInfo = remoteDataSource.getMyInfo(token);
                if (myInfo != null) {
                    myInfoLiveData.postValue(myInfo);
                }
            }
        });
        return myInfoLiveData;
    }

    public MutableLiveData<MyProjects> getMyProjects(final String token) {
        final MutableLiveData<MyProjects> myProjectsLiveData = new MutableLiveData<>();

        if (!remoteDataSource.checkInternetConnection()) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    ArrayList<ProjectsList> projectsList = localDataSource.getMyProjects();
                    MyProjects myProjects = new MyProjects();
                    myProjects.setProjectsList(projectsList);
                    myProjectsLiveData.postValue(myProjects);
                }
            });
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                MyProjects myProjects = remoteDataSource.getMyProjects(token);
                if (myProjects != null) {
                    myProjectsLiveData.postValue(myProjects);
                    localDataSource.storeMyProjects(myProjects.getProjectsList());
                }
            }
        });
        return myProjectsLiveData;
    }

    public MutableLiveData<CreateProject> createProject(final String name, final String description,
                                                        final String myRole, final String token) {
        final MutableLiveData<CreateProject> liveData = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                CreateProject createProjectData =
                        remoteDataSource.createProject(name, description, myRole, token);
                if (createProjectData != null) {
                    liveData.postValue(createProjectData);
                }
            }
        });
        return liveData;
    }

    public MutableLiveData<MyColleagues> getMyColleagues(final int projectId, final String token) {
        final MutableLiveData<MyColleagues> myColleaguesLiveData = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<ColleaguesList> colleaguesList = localDataSource.getMyColleagues(projectId);
                MyColleagues myColleagues = new MyColleagues();
                myColleagues.setColleaguesList(colleaguesList);
                myColleaguesLiveData.postValue(myColleagues);
                if (remoteDataSource.checkInternetConnection()) {
                    myColleagues = remoteDataSource.getMyColleagues(projectId, token);
                    if (myColleagues != null) {
                        myColleaguesLiveData.postValue(myColleagues);
                        localDataSource.storeMyColleagues(myColleagues.getColleaguesList(), projectId);
                    }
                }
            }
        });
        return myColleaguesLiveData;
    }

    public MutableLiveData<MySubordinates> getMySubordinates(final int projectId, final String token) {
        final MutableLiveData<MySubordinates> mySubordinatesLiveData = new MutableLiveData<>();


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<SubordinatesList> subordinatesList =
                        localDataSource.getMySubordinates(projectId);
                MySubordinates mySubordinates = new MySubordinates();
                mySubordinates.setSubordinatesList(subordinatesList);
                mySubordinatesLiveData.postValue(mySubordinates);
                if (remoteDataSource.checkInternetConnection()) {
                    mySubordinates = remoteDataSource.getMySubordinates(projectId, token);
                    if (mySubordinates != null) {
                        mySubordinatesLiveData.postValue(mySubordinates);
                        localDataSource.storeMySubordinates(mySubordinates.getSubordinatesList(), projectId);
                    }
                }
            }
        });
        return mySubordinatesLiveData;
    }

    public MutableLiveData<MyTasks> getMyTasks(final int projectId, final String token) {
        final MutableLiveData<MyTasks> myTasksLiveData = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<MyTasksList> myTasksList =
                        localDataSource.getMyTasks(projectId);
                MyTasks myTasks = new MyTasks();
                myTasks.setMyTasksList(myTasksList);
                myTasksLiveData.postValue(myTasks);
                if (remoteDataSource.checkInternetConnection()) {
                    myTasks = remoteDataSource.getMyTasks(projectId, token);
                    if (myTasks != null) {
                        myTasksLiveData.postValue(myTasks);
                        if (myTasks.getMyTasksList() != null) {
                            localDataSource.storeMyTasks(myTasks.getMyTasksList(), projectId);
                        }
                    }
                }
            }
        });
        return myTasksLiveData;
    }

    public MutableLiveData<SubordinatesTasks> getMySubordinatesTasks(final int projectId, final String token) {
        final MutableLiveData<SubordinatesTasks> subordinatesTasksLiveData = new MutableLiveData<>();


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<SubordinatesTasksList> subordinatesTasksList =
                        localDataSource.getSubordinatesTasks(projectId);
                SubordinatesTasks subordinatesTasks = new SubordinatesTasks();
                subordinatesTasks.setTasksList(subordinatesTasksList);
                subordinatesTasksLiveData.postValue(subordinatesTasks);
                if (remoteDataSource.checkInternetConnection()) {
                    subordinatesTasks =
                            remoteDataSource.getMySubordinatesTasks(projectId, token);
                    if (subordinatesTasks != null) {
                        subordinatesTasksLiveData.postValue(subordinatesTasks);
                        localDataSource.storeSubordinatesTasks(subordinatesTasks.getTasksList(), projectId);
                    }
                }
            }
        });

        return subordinatesTasksLiveData;
    }

    public void logError(final Err err) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                localDataSource.storeError(err);
            }
        });
    }

    public MutableLiveData<ArrayList<Err>> getErrors() {
        final MutableLiveData<ArrayList<Err>> errorsLiveData = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Err> errorsList =
                        localDataSource.getErrors();
                errorsLiveData.postValue(errorsList);
            }
        });
        return errorsLiveData;
    }

    public void deleteErrors() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                localDataSource.deleteErrors();
            }
        });
    }

    public void exit() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                localDataSource.exit();
            }
        });
    }

}
