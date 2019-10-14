package external.classloader;

import android.util.Log;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * Created by Angel on 27/03/2017.
 */

public class externalClassLoader {

    public static Class<?> loadClass (File dexOutputDir, String className, String classesDirectory, String fileName) {
        Class <?> result = null;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Log.v("loadDexClassses", "LoadDexClasses is only available for ICS or up");
        } else {

            File[] files = new File(classesDirectory).listFiles();

            if (files == null) {
                android.util.Log.d("LeaguesOfTheWorld", "There was no classes in" + classesDirectory);
            } else {

        /*
        Gets the absolute path to the application specific cache directory on the filesystem. These files will be ones that get
        deleted first when the device runs low on storage. There is no guarantee when these files will be deleted.
        */
                for (File file : files) {
                    if (file.getName().equals(fileName)) {
                        final DexClassLoader classloader = new DexClassLoader(
                                file.getAbsolutePath(), dexOutputDir.getAbsolutePath(),
                                null,
                                ClassLoader.getSystemClassLoader());

                        try {
                            result = classloader.loadClass(className);
                            break;
                        } catch (Exception e) {
                            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
                        }
                    }
                }
            }
        }

        return result;
    }
}
