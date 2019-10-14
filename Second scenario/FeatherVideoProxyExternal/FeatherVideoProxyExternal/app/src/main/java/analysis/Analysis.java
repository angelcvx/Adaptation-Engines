package analysis;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;

import java.util.Map;
import java.util.Random;

import app.previous.state.PreviousStateUser;
import monitors.MonitorCounter;
import reconfiguration.Adapt;


public class Analysis {
    private Class<?>[] objectProxied;
    private Adapt adapt;
    private PreviousStateUser state;
    private Context context;
    private boolean adapted;
    private Random rnd;

    public Analysis(Class<?>[] objects, Context c) {
        objectProxied = objects;
        adapt = new Adapt(objectProxied, c);
        state = new PreviousStateUser(c);
        context = c;
        adapted = false;
        rnd = new Random();
    }

    /**
     *
     * @param methodName Receives the method name intercepted by the handler
     * @param monitor Method receives all monitors necessaries for the application adaptation. In this case, we have only one.
     * @param args An array of arguments passed like parameters for the method "methodName"
     * @return
     */
    public Map<String, Class<?>> getMap(String methodName, MonitorCounter monitor, Object[] args) {

        try {

            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(context.CONNECTIVITY_SERVICE);

            double p = 100*(state.getNumberOfShares()/state.getNumberVideoCompressed());

            // First case. 75% of video sending and low battery
            if (level < 25 && !isCharging && p >= 75) {
                adapt.setModifiedMap();
                // Second case. 50% of video sending and slow network connecion
            } else if (!isConnectionFast(cm.getActiveNetworkInfo().getType(), cm.getActiveNetworkInfo().getSubtype()) && p >= 50) {
                adapt.setModifiedMap();
            }
            // Darker interface colors if battery level is lower than 25%
            if (level < 25 && !isCharging) {
                adapt.setColors();
            }

        } catch (Throwable e) {
            android.util.Log.d("VideoCompressor", e.getMessage());
        }

        return adapt.getMethods();
    }

    public PreviousStateUser getState() {
        return state;
    }

    public static boolean isConnectionFast(int type, int subType){
        if(type==ConnectivityManager.TYPE_WIFI){
            return true;
        }else if(type==ConnectivityManager.TYPE_MOBILE){
            switch(subType){
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        }else{
            return false;
        }
    }

    public boolean isAdapted() {
        return adapted;
    }


}
