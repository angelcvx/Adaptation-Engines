package analysis;

import java.util.List;
import java.util.Map;
import monitors.MonitorCounter;
import reconfiguration.AdaptLeagues;
import state.PreviousState;

/**
 * Created by Angel on 13/03/2017.
 */

public class Analysis {
    private Class<?> [] objectProxied;
    List<Integer> usedLeagues;
    private static int OFFSET = 97;
    private static int ALL_LEAGUES_SIZE = 26;
    private static int VALUE_CHANGE = 10;
    private AdaptLeagues adapt;
    private PreviousState state;
    private boolean newElement;

    public Analysis(Class<?> [] objects) {
        objectProxied = objects;
        adapt = new AdaptLeagues(objectProxied);
        state = new PreviousState();
        usedLeagues = state.getUsedLeagues();
        checkStateAndLoad();
    }

    public Map<String,Class<?>> getMap(String methodName, MonitorCounter monitor, Object[] args){
        newElement = false;

        try {
            if (args != null && (int) args[0] > ALL_LEAGUES_SIZE + OFFSET - 1) { //If user want to see all leagues again
                adapt.adaptVisibleTrue(usedLeagues);
            } else if (methodName.equals("getLeague") && !usedLeagues.contains(args[0])) {
                newElement = true;
                usedLeagues.add((int) args[0]);
                state.addLeague((int) args[0]);
                adapt.readLeague((int) args[0]);
            }

            if (monitor.getCounter() >= VALUE_CHANGE ) {
                if (adapt.isStateDefault()){
                    //adapt.adaptTransaction(usedLeagues);
                    //adapt.setModifiedMap();
                } else if (newElement) {
                    adapt.adaptVisibleFalse(usedLeagues);
                }
            }
        } catch (Throwable e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
        newElement = false;

        return adapt.getMethods();
    }

    public PreviousState getState() {
        return state;
    }

    public void checkStateAndLoad () {
        if (state.getMonitorCounterState() >= VALUE_CHANGE) {
            try {
                adapt.setModifiedMap();
                adapt.setLeaguesUsedAdaptation(usedLeagues);
                adapt.adaptVisibleFalse(usedLeagues);
            } catch (Throwable e){
                android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
            }
        } else {
            adapt.setDefaultMap();
        }
    }
}
