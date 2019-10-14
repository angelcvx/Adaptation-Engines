package reconfiguration;


import android.view.Menu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import external.classloader.externalClassLoader;
import load.LoadInterface;
import registry.com.selectmenu.MainActivity;

/**
 * Created by Angel on 08/03/2017.
 */

public class AdaptLeagues extends MainActivity{
    private static int OFFSET = 97;
    private static Class<?> [] objectProxied;
    private Map<String,Class<?>> methods;
    private boolean stateDefault;
    private static String externalClassName;
    private static String fileName = "classes.dex";
    private static String classesDirectory = "data/local/tmp/footballLoaders/";

    public AdaptLeagues (Class<?> [] classesName) {
        methods = new TreeMap<>();
        objectProxied = new Class<?> [2];
        objectProxied[0] = classesName[0];
        externalClassName = "load.LoadLeaguesAdaptation";
        stateDefault = true;
    }

    public void setDefaultMap () {
        for (Method m : LoadInterface.class.getMethods()) {
            add(m.getName(), objectProxied[0]);
        }
    }

    public void setModifiedMap () {
        objectProxied[1] = new externalClassLoader().loadClass(getOptimizedFile(), externalClassName, classesDirectory, fileName);

        for (Method m : LoadInterface.class.getMethods()) {
            add(m.getName(), objectProxied[1]);
        }
        stateDefault = false;
    }

    public Map<String,Class<?>> getMethods() {
        return methods;
    }

    public void add(String methodName, Class<?> method) {
        methods.put(methodName,method);
    }

    public void remove(String methodName) {
        methods.remove(methodName);
    }

    public void adaptVisibleFalse(List<Integer> usedLeagues) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Menu menu = super.getMenu();

        for (int i = 0; i < menu.size()-1; i++){
            if(!usedLeagues.contains(i+OFFSET)){
                menu.getItem(i).setVisible(false);
            }
        }
        menu.getItem(menu.size()-1).setVisible(true);
    }

    public void adaptVisibleTrue (List<Integer> usedLeagues) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Menu menu = super.getMenu();

        for (int i = 0; i < menu.size()-1; i++){
            if(!usedLeagues.contains(i+OFFSET)){
                menu.getItem(i).setVisible(true);
            }
        }
        menu.getItem(menu.size()-1).setVisible(false);
    }

    public void setLeaguesUsedAdaptation(List<Integer> usedLeagues) {
        try {
        Method m = objectProxied[1].getMethod("setUsedLeagues",List.class);
        m.invoke(objectProxied[1].newInstance(), usedLeagues);
        } catch (Throwable e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
    }

    public void adaptTransaction(List<Integer> usedLeagues) throws Throwable{
        try {
            Method m2 = objectProxied[1].getMethod("setLeagues", Map.class);
            Method m3 = objectProxied[0].getMethod("getLeagues");
            Map<Integer, String> leagues = (TreeMap<Integer, String>) m3.invoke(objectProxied[0].newInstance());
            setLeaguesUsedAdaptation(usedLeagues);
            m2.invoke(objectProxied[1].newInstance(), leagues);
            adaptVisibleFalse(usedLeagues);
        } catch (Throwable e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
    }

    public boolean isStateDefault() {
        return stateDefault;
    }

}
