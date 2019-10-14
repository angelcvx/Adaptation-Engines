package handler;

import android.content.Context;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import analysis.Analysis;
import app.previous.state.PreviousStateUser;
import compress.Compress;
import monitors.MonitorCounter;

public class MyInvocationHandler {
    MonitorCounter counterMonitor;
    private Analysis analysis;
    private PreviousStateUser state;
    private Compress compress;

    public MyInvocationHandler( Context c) {
        compress = new Compress();
        analysis = new Analysis(c);
        counterMonitor = new MonitorCounter();
        this.state = analysis.getState();
    }

    public void compress(File input, Context context, TextView text) {
        if (input!= null) {
            Object[] args = {input, context, context};
            counterMonitor.incCounter("compress", state, args);
            analysis.checkState("compress", counterMonitor, args);
            compress.compress(input, context, text);
        } else {
            counterMonitor.incCounter("share",state,null);
        }
    }

}
