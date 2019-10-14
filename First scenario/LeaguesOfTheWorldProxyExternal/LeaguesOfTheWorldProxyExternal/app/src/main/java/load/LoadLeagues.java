package load;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Angel on 03/03/2017.
 */

public class LoadLeagues implements LoadInterface {

    private static Map<Integer, String> leagues = new TreeMap<>();
    private static int OFFSET = 97;
    private static String[] allLeagues = {"germany_bundesliga.txt", "england_premier.txt", "spain_liga.txt", "italy_seriea.txt", "brazil_seriea.txt", "mexico_ligamx.txt", "usa_mls.txt", "netherlands_eredivisie.txt", "france_ligue1.txt", "argentina_primera.txt", "japan_j1league.txt", "portugal_primeira.txt",
            "russia_premier.txt", "china_super.txt", "belgium_pro.txt", "turkey_super.txt", "sweden_allsvenskan.txt", "switzerland_super.txt", "australia_a.txt", "poland_ekstraklasa.txt", "ecuador_seriea.txt", "uae_pro.txt", "paraguay_primera.txt", "scotland_premier.txt", "south_premier.txt", "uruguay_primera.txt"};

    public boolean loadLeagues(){
        String dirLeagues = Environment.getExternalStorageDirectory().getAbsolutePath() + "/InfoLeagues/";
        File directory = new File(dirLeagues);
        directory.mkdir();
        File[] files = directory.listFiles();
        if (files == null) {
            return false;
        } else {
            for (File file : files) {
                read(file);
            }
        }
        return true;
    }

    public void read(File f){
        try {
            FileReader fr = new FileReader(f.getAbsolutePath());
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null){
                sb.append(line + "\n");
                line = br.readLine();
            }
            br.close();
            fr.close();
            leagues.put(positionOf(f.getName())+OFFSET,sb.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public int positionOf(String league) {
        int result = 0;
        for (int i = 0; i < allLeagues.length; i++) {
            if (allLeagues[i].equals(league)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static Map<Integer, String> getLeagues() {
        return leagues;
    }

    public String getLeague (int pos){
        return leagues.get(pos);
    }

}
