package reconfiguration;

import android.view.Menu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import load.LoadInterface;
import registry.com.selectmenu.MainActivity;

/**
 * Created by Angel on 20/04/2017.
 */

public class AdaptLeagues extends MainActivity{
    private static int OFFSET = 97;
    private static Class<?> classToChange;
    private boolean stateDefault;

    public AdaptLeagues (Class<?> classToChange) {
        this.classToChange = classToChange;
        stateDefault = true;
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
        Method m = classToChange.getMethod("setUsedLeagues",List.class);
        m.invoke(classToChange.newInstance(), usedLeagues);
        } catch (Throwable e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
    }

    public boolean isStateDefault() {
        return stateDefault;
    }

}
