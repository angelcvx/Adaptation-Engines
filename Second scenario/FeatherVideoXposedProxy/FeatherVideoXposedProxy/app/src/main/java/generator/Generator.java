package generator;

import android.content.Context;

import java.lang.reflect.Proxy;

import compress.CompressInterface;
import handler.MyInvocationHandler;

/**
 * Created by Angel on 31/01/2017.
 */

public class Generator {

    /**
     * first class on "list" contains the default app functionality
     * */
    public Object generateObjectsFunction (Context c){

        Class<?>[] list = new Class<?>[1]; //Array of managed classes that share an interface
        try {
            list[0] = Class.forName("compress.Compress");
        } catch (ClassNotFoundException e) {
            android.util.Log.d("VideoCompressor", e.getMessage());
        }

        try {
            CompressInterface myProxy = (CompressInterface) Proxy.newProxyInstance(
                    CompressInterface.class.getClassLoader(), new Class[]{CompressInterface.class},
                    new MyInvocationHandler(list, c));

            return myProxy;
        } catch (Exception e){
            android.util.Log.d("VideoCompressor", e.getMessage());
        }

        return null;
    }
}
