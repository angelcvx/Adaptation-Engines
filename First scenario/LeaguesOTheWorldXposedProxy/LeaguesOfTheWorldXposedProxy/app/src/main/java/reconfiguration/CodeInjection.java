package reconfiguration;

/**
 * Created by Angel on 20/04/2017.
 */

import android.os.Environment;

import java.io.File;
import java.util.List;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XC_MethodReplacement;
import load.StaticLoadMethods;
import state.PreviousState;

public class CodeInjection implements IXposedHookLoadPackage {
    private PreviousState state;

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("registry.com.selectmenu"))
            return;

        XposedBridge.log("Se carga la app:" + lpparam.packageName);

        state = new PreviousState();

        if (state.getMonitorCounterState() >= StaticLoadMethods.CHANGE_VALUE) {
            findAndHookMethod("load.LoadLeagues", lpparam.classLoader, "loadLeagues", new XC_MethodReplacement() {

                @Override
                protected Boolean replaceHookedMethod(MethodHookParam param) throws Throwable {
                    String dirLeagues = Environment.getExternalStorageDirectory().getAbsolutePath() + "/InfoLeagues/";
                    File directory = new File(dirLeagues);
                    directory.mkdir();
                    File[] files = directory.listFiles();
                    if (files == null) {
                        return false;
                    } else {
                        for (File file : files) {
                            if (StaticLoadMethods.getUsedLeagues().contains(StaticLoadMethods.positionOf(file.getName()) + StaticLoadMethods.OFFSET)) {
                                StaticLoadMethods.read(file);
                            }
                        }
                    }
                    return true;
                }

            });

            findAndHookMethod("load.LoadLeagues", lpparam.classLoader, "loadLeague", int.class, new XC_MethodReplacement() {

                @Override
                protected Boolean replaceHookedMethod(MethodHookParam param) throws Throwable {
                    String dirLeagues = Environment.getExternalStorageDirectory().getAbsolutePath() + "/InfoLeagues/";
                    File directory = new File(dirLeagues);
                    directory.mkdir();
                    File[] files = directory.listFiles();
                    String leagueToLoad = StaticLoadMethods.getAllLeagues()[(int) param.args[0] - StaticLoadMethods.OFFSET];
                    if (files == null) {
                        return false;
                    } else {
                        for (File file : files) {
                            if (file.getName().equals(leagueToLoad)) {
                                StaticLoadMethods.read(file);
                            }
                        }
                    }
                    return true;
                }

            });

            findAndHookMethod("load.LoadLeagues", lpparam.classLoader, "setUsedLeagues", List.class, new XC_MethodReplacement() {

                @Override
                protected Boolean replaceHookedMethod(MethodHookParam param) throws Throwable {
                    StaticLoadMethods.setUsedLeagues((List<Integer>) param.args[0]);
                    return null;
                }
            });

            findAndHookMethod("load.LoadLeagues", lpparam.classLoader, "getLeague", int.class, new XC_MethodReplacement() {

                @Override
                protected String replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return StaticLoadMethods.getLeague((int) param.args[0]);
                }
            });

        }
    }

}