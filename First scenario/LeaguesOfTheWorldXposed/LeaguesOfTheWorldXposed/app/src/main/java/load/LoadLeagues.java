package load;

import android.os.Environment;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Angel on 20/04/2017.
 */

public class LoadLeagues implements LoadInterface{

    public boolean loadLeagues(){
        String dirLeagues = Environment.getExternalStorageDirectory().getAbsolutePath() + "/InfoLeagues/";
        File directory = new File(dirLeagues);
        directory.mkdir();
        File[] files = directory.listFiles();
        if (files == null) {
            return false;
        } else {
            for (File file : files) {
                StaticLoadMethods.read(file);
            }
        }
        return true;
    }

    public boolean loadLeague(int pos){
        return true;
    }

    public void setUsedLeagues(List<Integer> usedLeagues) {
        StaticLoadMethods.setUsedLeagues(usedLeagues);
    }

    public static Map<Integer, String> getLeagues() {
        return StaticLoadMethods.getLeagues();
    }

    public String getLeague (int pos){
        return StaticLoadMethods.getLeague(pos);
    }

}
