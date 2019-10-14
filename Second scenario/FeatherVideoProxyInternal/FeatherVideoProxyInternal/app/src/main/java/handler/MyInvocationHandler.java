package handler;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import analysis.Analysis;
import app.previous.state.PreviousStateUser;
import monitors.MonitorCounter;

public class MyInvocationHandler implements InvocationHandler {
    MonitorCounter counterMonitor;
    private Map<String,Class<?>> methods;
    private Analysis analysis;
    private PreviousStateUser state;

    /** User should instance his/her monitors here (in this case, only one, counterMonitor, is instantiated.
     *
     * @param objectProxied List of reconfiguration alternatives
     * @param c Application context
     */
    public MyInvocationHandler(Class<?>[] objectProxied, Context c) {
        methods = new TreeMap<>();
        analysis = new Analysis(objectProxied, c);
        counterMonitor = new MonitorCounter();
        this.state = analysis.getState();
    }

    /** This method call to Analysis module, sending to it the method name, all the monitors (one in this case, counterMonitor) and the method arguments (args). User should instantiate his/her monitors
     * and to send it to the Analysis module here.
     *
     * @param object Result of the method
     * @param method Method to be executed
     * @param args Arguments of the method to be executed
     * @return
     */
    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        if (args[0] != null) {
            Object result = new Object();
            counterMonitor.incCounter(method.getName(), state, args);
            methods = analysis.getMap(method.getName(), counterMonitor, (args));
            try {
                result = method.invoke(methods.get(method.getName()).newInstance(), args);
            } catch (Throwable e) {
                android.util.Log.d("CompressVideo", e.getMessage());
            }
            return result;
        } else {
            counterMonitor.incCounter("share", state, args);
            return 0;
        }
    }

}
