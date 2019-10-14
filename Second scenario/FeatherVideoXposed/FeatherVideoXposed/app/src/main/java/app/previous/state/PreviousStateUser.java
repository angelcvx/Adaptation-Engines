package app.previous.state;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import state.PreviousStateShared;

public class PreviousStateUser {

    private PreviousStateShared state;

    public PreviousStateUser(Context c){
        state = new PreviousStateShared(c);
    }

    //User saves information about the user behaviour using functions provided in the state class.

    /**
     * Methods to be implemented by the user in order to save information about previous application executions.
     * state object provides the necessary functionality to save/ delete and modify it.
     * state.get(key, default_value) -> return the value associated to the key "key". If "key" does not exist, "default_value" is its new value.
     * state.insert(key, new_value) -> creates a new pair if key does not exist. If yes, modify it
     * state.delete(key) -> deletes the pair with key "key"
     * @return
     */

    public int getNumberVideoCompressed(String videoName) {
        return state.get(videoName, 0);
    }

    public void setVideoTimes (String videoName ,int n) {
        state.insert(videoName, n);
    }

    public void incNumberVideoCompressed (String videoName) {
        state.insert(videoName,state.get(videoName, 0) + 1);
    }

    public void incNumberOfShares () {
        state.insert("shares",state.get("shares", 0) + 1);
    }

    public int getNumberOfShares() {
        return state.get("shares",0);
    }

    public void incNumberVideoCompressed () {
        state.insert("compressed",state.get("compressed", 0) + 1);
    }

    public int getNumberVideoCompressed () {
        return state.get("compressed",0);
    }

}
