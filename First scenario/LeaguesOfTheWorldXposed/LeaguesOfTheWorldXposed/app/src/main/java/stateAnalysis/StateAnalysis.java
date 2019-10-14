package stateAnalysis;

import java.util.List;

import monitors.MonitorCounter;
import reconfiguration.AdaptLeagues;
import state.PreviousState;

/**
 * Created by Angel on 13/03/2017.
 */

public class StateAnalysis {
    List<Integer> usedLeagues;
    private static int OFFSET = 97;
    private static int ALL_LEAGUES_SIZE = 26;
    private static int VALUE_CHANGE = 5;
    private AdaptLeagues adapt;
    private PreviousState state;
    private boolean newElement;
    private boolean needToLoadLeague;

    public StateAnalysis(Class<?>  classToChange) {
        adapt = new AdaptLeagues(classToChange);
        state = new PreviousState();
        usedLeagues = state.getUsedLeagues();
        checkStateAndLoad();
    }

    public boolean refreshState (String methodName, MonitorCounter monitor, Object[] args){
        newElement = false;
        needToLoadLeague = false;

        try {
            if (args != null && (int) args[0] > ALL_LEAGUES_SIZE + OFFSET - 1) { //If user want to see all leagues again
                adapt.adaptVisibleTrue(usedLeagues);
            } else if (args != null && methodName.equals("getLeague") && !usedLeagues.contains(args[0])) {
                newElement = true;
                usedLeagues.add((int) args[0]);
                state.addLeague((int) args[0]);
                needToLoadLeague = true;
               // adapt.readLeague((int) args[0]);
            }

            if (monitor.getCounter() >= VALUE_CHANGE ) {
                if (!state.isDefaultState() && newElement) {
                    adapt.adaptVisibleFalse(usedLeagues);
                }
            }
        } catch (Throwable e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
        return needToLoadLeague;
    }

    public PreviousState getState() {
        return state;
    }

    public void checkStateAndLoad () {
        if (state.getMonitorCounterState() >= VALUE_CHANGE) {
            try {
                state.setDefaultState();
                adapt.setLeaguesUsedAdaptation(usedLeagues);
                adapt.adaptVisibleFalse(usedLeagues);
            } catch (Throwable e) {
                android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
            }

        }
    }
}
