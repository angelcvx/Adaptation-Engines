package monitors;

import state.PreviousState;

/**
 * Created by Angel on 13/03/2017.
 */

public class MonitorCounter {
    private static int counter;
    private static int VALUE_CHANGE = 10;

    public MonitorCounter () {
        counter = 0;
    }

    public void incCounter(String methodName, PreviousState state) {
        if (methodName.equals("getLeague") && counter < VALUE_CHANGE) {
            counter++;
            state.setMonitorCounterState(counter);
        }
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int count) {
        counter = count;
    }
}
