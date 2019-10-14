package handler;

import stateAnalysis.StateAnalysis;
import load.LoadInterface;
import load.LoadLeagues;
import monitors.MonitorCounter;
import state.PreviousState;

public class MyInvocationHandler implements LoadInterface{
    MonitorCounter counterMonitor;
    LoadLeagues load = new LoadLeagues();
    private StateAnalysis stateAnalysis;
    private PreviousState state;

    public MyInvocationHandler() {
        stateAnalysis = new StateAnalysis(load.getClass());
        counterMonitor = new MonitorCounter();
        this.state = stateAnalysis.getState();
        counterMonitor.setCounter(state.getMonitorCounterState());
    }

    @Override
    public boolean loadLeagues() {
        counterMonitor.incCounter("loadLeagues", state);
        stateAnalysis.refreshState("getLeague", counterMonitor, null);
        return load.loadLeagues();
    }

    @Override
    public String getLeague(int pos) {
        counterMonitor.incCounter("getLeague", state);
        Object [] args = {pos};
        if (stateAnalysis.refreshState("getLeague", counterMonitor, args)) {
            load.loadLeague(pos);
        }
        return load.getLeague(pos);
    }
}
