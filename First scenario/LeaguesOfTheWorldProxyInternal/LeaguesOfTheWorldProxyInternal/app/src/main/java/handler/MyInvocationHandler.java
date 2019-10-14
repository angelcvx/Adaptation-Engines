package handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;
import analysis.Analysis;
import monitors.MonitorCounter;
import state.PreviousState;

public class MyInvocationHandler implements InvocationHandler {
    MonitorCounter counterMonitor;
    private Map<String,Class<?>> methods;
    private Analysis analysis;
    private PreviousState state;

    public MyInvocationHandler(Class<?> [] objectProxied) {
        methods = new TreeMap<>();
        analysis = new Analysis(objectProxied);
        counterMonitor = new MonitorCounter();
        this.state = analysis.getState();
        counterMonitor.setCounter(state.getMonitorCounterState());
    }

    @Override public Object invoke(Object object, Method method, Object[] args) {
        Object result = new Object();
        counterMonitor.incCounter(method.getName(),state);
        methods = analysis.getMap(method.getName(), counterMonitor, (args));
        try{
            result = method.invoke(methods.get(method.getName()).newInstance(),args);
        } catch (Throwable e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
        return result;
    }

}
