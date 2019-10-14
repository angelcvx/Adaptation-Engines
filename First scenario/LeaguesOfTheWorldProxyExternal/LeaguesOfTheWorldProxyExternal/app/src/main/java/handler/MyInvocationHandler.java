package handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import analysis.Analysis;
import monitors.MonitorCounter;
import state.PreviousState;

public class MyInvocationHandler implements InvocationHandler {
    MonitorCounter counterMonitor;
    private Map<String,Class<?>> methods;
    private Map<Class<?>, Class<?>> WRAPPERS_TO_PRIMITIVES;
    private Analysis analysis;
    private PreviousState state;


    public MyInvocationHandler(Class<?> [] classesName) {
        methods = new TreeMap<>();
        WRAPPERS_TO_PRIMITIVES = new HashMap<>();
        inizialicePrimitives();
        analysis = new Analysis(classesName);
        counterMonitor = new MonitorCounter();
        this.state = analysis.getState();
        counterMonitor.setCounter(state.getMonitorCounterState());
    }

    @Override public Object invoke(Object object, Method method, Object[] args) {
        Object result = new Object();
        String name = method.getName();

        counterMonitor.incCounter(name,state);
        methods = analysis.getMap(name, counterMonitor, (args));
        try{
            Object classe = methods.get(name).newInstance();
            if (args != null && args.length > 0) {
                Class<?> c = args[0].getClass();
                if(!c.isPrimitive()) {
                     c = getPrimitiveClass(c);
                }
                result = methods.get(method.getName()).getMethod(name, c).invoke(classe, args);
            } else {
                result = methods.get(method.getName()).getMethod(name).invoke(classe, args);
            }
        } catch (Throwable e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
        return result;
    }

    public Class getPrimitiveClass (Class c) {
        if (WRAPPERS_TO_PRIMITIVES.containsKey(c)) {
            return WRAPPERS_TO_PRIMITIVES.get(c);
        } else {
            return c;
        }
    }

    public void inizialicePrimitives () {
        WRAPPERS_TO_PRIMITIVES.put(Boolean.class, boolean.class);
        WRAPPERS_TO_PRIMITIVES.put(Byte.class, byte.class);
        WRAPPERS_TO_PRIMITIVES.put(Character.class, char.class);
        WRAPPERS_TO_PRIMITIVES.put(Double.class, double.class);
        WRAPPERS_TO_PRIMITIVES.put(Float.class, float.class);
        WRAPPERS_TO_PRIMITIVES.put(Integer.class, int.class);
        WRAPPERS_TO_PRIMITIVES.put(Long.class, long.class);
        WRAPPERS_TO_PRIMITIVES.put(Short.class, short.class);
    }

}
