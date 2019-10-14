package generator;

import java.lang.reflect.Proxy;

import load.LoadInterface;
import handler.MyInvocationHandler;

/**
 * Created by Angel on 31/01/2017.
 */

public class Generator {
    private static MyInvocationHandler handler;

    public Object generateObjectsFunction (){

        Class<?> [] list = new Class<?>[1]; //Array of managed classes that share an interface
        try {
            list[0] = Class.forName("load.LoadLeagues");
        } catch (ClassNotFoundException e) {
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }

        handler = new MyInvocationHandler(list);
        try {
            LoadInterface myProxy = (LoadInterface) Proxy.newProxyInstance(
                    LoadInterface.class.getClassLoader(), new Class[]{LoadInterface.class},
                    handler);
            return myProxy;

        } catch (Exception e){
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }

        return null;
    }
}
