package load;

import java.io.File;

/**
 * Created by Angel on 08/03/2017.
 */

public interface LoadInterface {

    boolean  loadLeagues();
    void read (File f);
    int positionOf (String league);
    String getLeague (int pos);

}
