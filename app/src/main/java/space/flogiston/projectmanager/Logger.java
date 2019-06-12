package space.flogiston.projectmanager;

import android.content.Context;

import java.util.Date;

import space.flogiston.projectmanager.data.entities.Err;

public class Logger {
    public Logger (Context context, String errorMsg) {
        Err error = new Err();
        error.errorMessage = errorMsg;
        Date now = new Date();
        error.timestamp = now.getTime();

        ((ProjectManager) context.getApplicationContext()).getRepository().logError(error);
    }
}
