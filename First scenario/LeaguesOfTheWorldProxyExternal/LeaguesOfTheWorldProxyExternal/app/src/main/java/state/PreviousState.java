package state;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Angel on 16/03/2017.
 */

public class PreviousState {
    private List<Integer> usedLeagues;
    File stateFile;
    String dirStateFile;

  public PreviousState () {
      usedLeagues = new ArrayList<>();
      getFile();
  }

   public void getFile() {
       dirStateFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LeaguesState/";
       File directory = new File(dirStateFile);
       directory.mkdir();
       File[] files = directory.listFiles();
       if (files.length != 0) {
           stateFile = files[0];
       } else {
           stateFile = null;
       }
   }

    public void setStateFile () {
        dirStateFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LeaguesState/";
        File directory = new File(dirStateFile);
        File[] files = directory.listFiles();
        stateFile = files[0];
    }

    public int getMonitorCounterState() {
        int result = 0;
        try {
            if (stateFile != null){
                BufferedReader br = new BufferedReader(new FileReader(stateFile));
                result = Integer.valueOf(br.readLine());
                br.close();
            }
        } catch (Exception e) {
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
        return result;
    }

    public List<Integer> getUsedLeagues() {
        try {
            if (stateFile == null) {
                return usedLeagues;
            } else {
                BufferedReader br = new BufferedReader(new FileReader(stateFile));
                br.readLine();
                String usedLeaguesFile = br.readLine();
                for (String league : usedLeaguesFile.split(";")){
                    usedLeagues.add(Integer.valueOf(league));
                }
            }
        } catch (Exception e) {
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
        return usedLeagues;
    }

    public void addLeague(int leagueValue){
        try {
            BufferedReader br = new BufferedReader(new FileReader(stateFile));
            StringBuilder sb = new StringBuilder();
            sb.append(br.readLine() + "\n"); //Never null, because analysis module calls monitor first
            String usedLeaguesFile = br.readLine();
            if (usedLeaguesFile != null){
                sb.append(usedLeaguesFile + leagueValue + ";");
            } else {
                sb.append(leagueValue + ";");
            }
            FileOutputStream fileOut = new FileOutputStream(stateFile.getAbsolutePath());
            fileOut.write(sb.toString().getBytes());
            fileOut.close();
        } catch (Exception e) {
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
}

    public void setMonitorCounterState(int count) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(count + "\n");
            if (stateFile != null) {
                BufferedReader br = new BufferedReader(new FileReader(stateFile));
                br.readLine();
                String usedLeaguesFile = br.readLine();
                if (usedLeaguesFile != null) {
                    sb.append(usedLeaguesFile);
                }
                FileOutputStream fileOut = new FileOutputStream(stateFile.getAbsolutePath());
                fileOut.write(sb.toString().getBytes());
                fileOut.close();
            } else {
                FileOutputStream fileOut = new FileOutputStream(dirStateFile + "state.txt");
                fileOut.write(sb.toString().getBytes());
                fileOut.close();
                setStateFile();
            }

        } catch (Exception e) {
            android.util.Log.d("LeaguesOfTheWorld", e.getMessage());
        }
    }
}
