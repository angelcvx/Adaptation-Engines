package load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Angel on 20/04/2017.
 */

public class StaticLoadMethods {

    private static String[] allLeagues = {"germany_bundesliga.txt", "england_premier.txt", "spain_liga.txt", "italy_seriea.txt", "brazil_seriea.txt", "mexico_ligamx.txt", "usa_mls.txt", "netherlands_eredivisie.txt", "france_ligue1.txt", "argentina_primera.txt", "japan_j1league.txt", "portugal_primeira.txt",
            "russia_premier.txt", "china_super.txt", "belgium_pro.txt", "turkey_super.txt", "sweden_allsvenskan.txt", "switzerland_super.txt", "australia_a.txt", "poland_ekstraklasa.txt", "ecuador_seriea.txt", "uae_pro.txt", "paraguay_primera.txt", "scotland_premier.txt", "south_premier.txt", "uruguay_primera.txt"};
    private static List<Integer> usedLeagues;
    private static Map<Integer, String> leagues = new TreeMap<>();
    public static int OFFSET = 97;
    public static int CHANGE_VALUE = 10;

    public static void read(File f){
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

    public static int positionOf(String league) {
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

    public static String getLeague (int pos){
        return leagues.get(pos);
    }

    public static String[] getAllLeagues() {
        return allLeagues;
    }

    public static List<Integer> getUsedLeagues() {
        return usedLeagues;
    }

    public static void setUsedLeagues(List<Integer> leagues) {
        usedLeagues = leagues;
    }
}
