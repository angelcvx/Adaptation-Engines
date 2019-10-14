package analysis;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import app.previous.state.PreviousStateUser;
import compress.Compress;
import monitors.MonitorCounter;
import reconfiguration.Adapt;

/**
 * Created by Angel on 13/03/2017.
 */

public class Analysis {
    private Adapt adapt;
    private PreviousStateUser state;
    private Context context;
    private Compress compress;

    public Analysis( Context c) {
        adapt = new Adapt(c);
        state = new PreviousStateUser(c);
        context = c;
        compress = new Compress();
    }

    /**
     *
     * @param methodName Receives the method name intercepted by the handler
     * @param monitor Method receives all monitors necessaries for the application adaptation. In this case, we have only one.
     * @param args An array of arguments passed like parameters for the method "methodName"
     * @return
     */
    public void checkState (String methodName, MonitorCounter monitor, Object[] args) {

        try {

            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            // Darker interface colors if battery level is lower than 25%
            if (level < 25 && !isCharging) {
                adapt.setColors();
            }

        } catch (Throwable e) {
            android.util.Log.d("Analysis", e.getMessage());
        }
        }

    public PreviousStateUser getState() {
        return state;
    }


}
