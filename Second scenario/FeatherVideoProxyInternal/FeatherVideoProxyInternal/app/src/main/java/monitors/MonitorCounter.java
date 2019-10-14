package monitors;

import java.io.File;

import app.previous.state.PreviousStateUser;

public class MonitorCounter {

    public MonitorCounter() {

    }

    /**
     *
     * @param methodName Monitor receives the method name and operates in function of it
     * @param state Monitor modifies the information contained in the database if is necessary
     */

    public void incCounter(String methodName, PreviousStateUser state, Object[] args) {
        if (methodName.equals("compress")) {
            state.incNumberVideoCompressed();
            state.incNumberVideoCompressed(((File) args[0]).getName());
        } else if (methodName.equals("share")) {
            state.incNumberOfShares();
        }
    }



}
